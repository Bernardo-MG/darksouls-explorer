
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingMatchAndReturnWithOrder;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.model.DefaultItem;
import com.bernardomg.darksouls.explorer.item.model.DefaultItemSource;
import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;
import com.bernardomg.darksouls.explorer.persistence.AbstractQueryExecutor;

@Component
public final class DefaultItemQueries extends AbstractQueryExecutor
        implements ItemQueries {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultItemQueries.class);

    private final Neo4jClient   client;

    public DefaultItemQueries(final Neo4jClient clnt) {
        super(clnt, Renderer.getDefaultRenderer());

        client = clnt;
    }

    @Override
    public final Page<Item> findAll(final Pageable page) {
        final Node m;
        final AliasedExpression name;
        final OngoingMatchAndReturnWithOrder statementBuilder;

        m = Cypher.node("Item").named("i");
        name = m.property("name").as("name");
        statementBuilder = Cypher.match(m)
                .returning(name, m.property("description").as("description"))
                // Order by
                .orderBy(name.asName().ascending());

        return fetch(statementBuilder, this::toItem, page);
    }

    @Override
    public final Iterable<ItemSource> findAllSources() {
        final String query;
        final Collection<ItemSource> data;
        final Collection<Map<String, Object>> read;

        // @formatter:off
        query = "MATCH\r\n"
                + "        (s)-[rel:DROPS|SELLS|STARTS_WITH|REWARDS]->(i:Item)\r\n"
                + "RETURN\r\n" + "        i.name AS item,\r\n"
                + "        s.name AS source,\r\n" + "        CASE type(rel)\r\n"
                + "        WHEN 'DROPS' THEN 'drop'\r\n"
                + "        WHEN 'SELLS' THEN 'sold'\r\n"
                + "        WHEN 'STARTS_WITH' THEN 'starting'\r\n"
                + "        WHEN 'REWARDS' THEN 'covenant_reward'\r\n"
                + "        END AS relationship\r\n" + "UNION ALL\r\n"
                + "MATCH\r\n" + "        (i:Item:StartingGift)\r\n"
                + "RETURN\r\n" + "        i.name AS item,\r\n"
                + "        \"Starting gift\" AS source,\r\n"
                + "        \"starting_gift\" AS relationship";
        // @formatter:on
        LOGGER.debug("Query: {}", query);

        // Data is fetched and mapped
        read = client.query(query).fetch().all();
        data = read.stream().map(this::toItemSource)
                .collect(Collectors.toList());

        return data;
    }

    private final Item toItem(final Map<String, Object> record) {
        final Item item;

        item = new DefaultItem();
        item.setName((String) record.getOrDefault("name", ""));
        item.setDescription((String) record.getOrDefault("description", ""));

        return item;
    }

    private final ItemSource toItemSource(final Map<String, Object> record) {
        final ItemSource source;

        source = new DefaultItemSource();
        source.setItem((String) record.getOrDefault("item", ""));
        source.setSource((String) record.getOrDefault("source", ""));
        source.setRelationship(
                (String) record.getOrDefault("relationship", ""));

        return source;
    }

}
