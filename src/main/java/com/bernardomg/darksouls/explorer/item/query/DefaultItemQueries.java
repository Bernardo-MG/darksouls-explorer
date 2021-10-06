
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Relationship;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingMatchAndReturnWithOrder;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingAndReturn;
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

    private final Renderer cypherRenderer = Renderer.getDefaultRenderer();

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
        final Node s;
        final Node i;
        final Node startingGift;
        final Relationship rel;
        final OngoingReadingAndReturn firstStatementBuilder;
        final OngoingReadingAndReturn secondStatementBuilder;
        final Statement statementBuilder;

        s = Cypher.anyNode().named("s");
        i = Cypher.node("Item").named("i");
        rel = s.relationshipTo(i, "DROPS", "SELLS", "STARTS_WITH", "REWARDS")
                .named("rel");
        firstStatementBuilder = Cypher.match(rel).returning(
                i.property("name").as("item"), s.property("name").as("source"),
                Functions.type(rel).as("relationship"));

        startingGift = Cypher.node("Item", "StartingGift").named("i");
        secondStatementBuilder = Cypher.match(startingGift).returning(
                i.property("name").as("item"),
                Cypher.literalOf("Starting gift").as("source"),
                Cypher.literalOf("starting_gift").as("relationship"));

        statementBuilder = Cypher.union(Arrays.asList(
                firstStatementBuilder.build(), secondStatementBuilder.build()));

        query = cypherRenderer.render(statementBuilder);
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
