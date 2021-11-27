
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        return queryExecutor.fetch(
                "MATCH (i:Item) RETURN i.name AS name, i.description AS description",
                this::toItem, page);
    }

    @Override
    public final Page<ItemSource> findSources(final Long id,
            final Pageable page) {
        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetch(
                "MATCH (s)-[rel:DROPS|SELLS|STARTS_WITH|REWARDS|CHOSEN_FROM]->(i:Item) WHERE id(i) = $id RETURN i.name AS item, s.name AS source, type(rel) AS relationship",
                this::toItemSource, params, page);
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
