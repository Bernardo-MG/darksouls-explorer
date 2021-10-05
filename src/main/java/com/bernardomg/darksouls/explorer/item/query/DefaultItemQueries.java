
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingMatchAndReturnWithOrder;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesLimit;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesSkip;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.model.DefaultItem;
import com.bernardomg.darksouls.explorer.item.model.DefaultItemSource;
import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;

@Component
public final class DefaultItemQueries implements ItemQueries {

    /**
     * Logger.
     */
    private static final Logger   LOGGER         = LoggerFactory
            .getLogger(DefaultItemQueries.class);

    private static final Renderer cypherRenderer = Renderer
            .getDefaultRenderer();

    private final Neo4jClient     client;

    public DefaultItemQueries(final Neo4jClient clnt) {
        super();

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

    private final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> statementBuilder,
            final Function<Map<String, Object>, T> mapper,
            final Pageable page) {
        final String query;
        final List<T> data;
        final ResultStatement statement;
        final Collection<Map<String, Object>> read;
        final String countTemplate;
        final String countQuery;
        final Long count;

        countTemplate = "CALL {%s} RETURN COUNT(*)";
        countQuery = String.format(countTemplate,
                cypherRenderer.render(statementBuilder.build()));

        // Pagination
        if (page != Pageable.unpaged()) {
            if (statementBuilder instanceof TerminalExposesSkip) {
                ((TerminalExposesSkip) statementBuilder)
                        .skip(page.getPageNumber());
            }
            if (statementBuilder instanceof TerminalExposesLimit) {
                ((TerminalExposesLimit) statementBuilder)
                        .limit(page.getPageSize());
            }
        }

        statement = statementBuilder.build();
        query = cypherRenderer.render(statement);

        LOGGER.debug("Query: {}", query);
        LOGGER.debug("Count: {}", countQuery);

        count = client.query(countQuery).fetchAs(Long.class).first().get();

        if (count == 0) {
            data = Collections.emptyList();
        } else {
            // Data is fetched and mapped
            read = client.query(query).fetch().all();
            data = read.stream().map(mapper).collect(Collectors.toList());
        }

        return new PageImpl<>(data, page, count);
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
