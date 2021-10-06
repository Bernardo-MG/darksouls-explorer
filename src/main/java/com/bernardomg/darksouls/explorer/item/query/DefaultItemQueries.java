
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Map;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Relationship;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingMatchAndReturnWithOrder;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingAndReturn;
import org.neo4j.cypherdsl.core.renderer.Renderer;
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

    public DefaultItemQueries(final Neo4jClient clnt) {
        super(clnt, Renderer.getDefaultRenderer());
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
    public final Page<ItemSource> findAllSources(final Pageable page) {
        final Node s;
        final Node i;
        final Relationship rel;
        final OngoingReadingAndReturn statementBuilder;

        s = Cypher.anyNode().named("s");
        i = Cypher.node("Item").named("i");
        rel = s.relationshipTo(i, "DROPS", "SELLS", "STARTS_WITH", "REWARDS",
                "CHOSEN_FROM").named("rel");
        statementBuilder = Cypher.match(rel).returning(
                i.property("name").as("item"), s.property("name").as("source"),
                Functions.type(rel).as("relationship"));

        return fetch(statementBuilder, this::toItemSource, page);
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
        final String type;

        switch ((String) record.getOrDefault("relationship", "")) {
            case "DROPS":
                type = "drop";
                break;
            case "SELLS":
                type = "sold";
                break;
            case "STARTS_WITH":
                type = "starting";
                break;
            case "REWARDS":
                type = "covenant_reward";
                break;
            default:
                type = "";
        }

        source = new DefaultItemSource();
        source.setItem((String) record.getOrDefault("item", ""));
        source.setSource((String) record.getOrDefault("source", ""));
        source.setRelationship(type);

        return source;
    }

}
