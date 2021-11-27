
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Map;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Relationship;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.model.DefaultItem;
import com.bernardomg.darksouls.explorer.item.model.DefaultItemSource;
import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;

@Component
public final class DefaultItemQueries implements ItemQueries {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultItemQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final Page<Item> findAll(final Pageable page) {
        final Node item;
        final AliasedExpression name;
        final BuildableStatement<ResultStatement> statementBuilder;

        item = Cypher.node("Item").named("i");
        name = item.property("name").as("name");
        statementBuilder = Cypher.match(item).returning(name,
                item.property("description").as("description"));

        return queryExecutor.fetch(statementBuilder, this::toItem, page);
    }

    @Override
    public final Page<ItemSource> findSources(final Long id,
            final Pageable page) {
        final Node s;
        final Node i;
        final Relationship rel;
        final BuildableStatement<ResultStatement> statementBuilder;

        s = Cypher.anyNode().named("s");
        i = Cypher.node("Item").named("i");
        rel = s.relationshipTo(i, "DROPS", "SELLS", "STARTS_WITH", "REWARDS",
                "CHOSEN_FROM").named("rel");
        statementBuilder = Cypher.match(rel)
                .where(i.internalId().isEqualTo(Cypher.literalOf(id)))
                .returning(i.property("name").as("item"),
                        s.property("name").as("source"),
                        Functions.type(rel).as("relationship"));

        return queryExecutor.fetch(statementBuilder, this::toItemSource, page);
    }

    private final Item toItem(final Map<String, Object> record) {
        final Iterable<String> description;

        description = Arrays.asList(
                ((String) record.getOrDefault("description", "")).split("\\|"));

        return new DefaultItem((String) record.getOrDefault("name", ""),
                description);
    }

    private final ItemSource toItemSource(final Map<String, Object> record) {
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

        return new DefaultItemSource((String) record.getOrDefault("item", ""),
                (String) record.getOrDefault("source", ""), type);
    }

}
