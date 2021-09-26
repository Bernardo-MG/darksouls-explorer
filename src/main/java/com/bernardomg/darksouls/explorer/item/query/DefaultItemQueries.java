
package com.bernardomg.darksouls.explorer.item.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.model.DefaultItem;
import com.bernardomg.darksouls.explorer.item.model.Item;

@Component
public final class DefaultItemQueries implements ItemQueries {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultItemQueries.class);

    private final Driver        driver;

    public DefaultItemQueries(final Driver drv) {
        super();

        driver = drv;
    }

    @Override
    public final Iterable<Item> findAll() {
        final Result rows;
        final String query;
        final Collection<Item> items;
        Record record;
        Item item;

        query = "MATCH (i:Item) RETURN i.name, i.description";
        LOGGER.debug("Query: {}", query);

        items = new ArrayList<>();
        try (final Session session = driver.session()) {
            rows = session.run(query);

            while (rows.hasNext()) {
                record = rows.next();

                item = new DefaultItem();
                item.setName(record.get("name", ""));
                item.setDescription(record.get("description", ""));
                items.add(item);
            }
        }

        return items;
    }

    @Override
    public final Optional<Item> findOneByName(final String name) {
        final Result rows;
        final String query;
        final Map<String, Object> parameters;
        final Item item;
        final Record record;

        parameters = new HashMap<>();
        parameters.put("item", name);

        query = "MATCH (s)-[rel:DROPS|SELLS]->(i:Item), (s)-[:LOCATED_IN]->(l) WHERE i.name = $item RETURN i.name, s.name, l.name, rel.price";
        LOGGER.debug("Query: {}", query);

        item = new DefaultItem();
        try (final Session session = driver.session()) {
            rows = session.run(query, parameters);

            if (rows.hasNext()) {
                record = rows.next();
                item.setName(record.get("name", ""));
                item.setDescription(record.get("description", ""));
            }
        }

        return Optional.of(item);
    }

}
