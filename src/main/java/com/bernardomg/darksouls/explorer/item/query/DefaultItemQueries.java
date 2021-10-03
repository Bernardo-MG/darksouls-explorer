
package com.bernardomg.darksouls.explorer.item.query;

import java.util.ArrayList;
import java.util.Collection;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        // @formatter:off
        query = "MATCH\r\n"
                + "        (i:Item)\r\n"
                + "RETURN\r\n"
                + "        i.name AS name,\r\n"
                + "        i.description AS description\r\n"
                + "ORDER BY\r\n"
                + "        name ASC";
        // @formatter:on
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
    public final Iterable<ItemSource> findAllSources() {
        final Result rows;
        final String query;
        final Collection<ItemSource> sources;
        Record record;
        ItemSource source;

        // @formatter:off
        query = "MATCH\r\n"
                + "        (s)-[rel:DROPS|SELLS|STARTS_WITH|REWARDS]->(i:Item)\r\n"
                + "RETURN\r\n"
                + "        i.name AS item,\r\n"
                + "        s.name AS source,\r\n"
                + "        CASE type(rel)\r\n"
                + "        WHEN 'DROPS' THEN 'drop'\r\n"
                + "        WHEN 'SELLS' THEN 'sold'\r\n"
                + "        WHEN 'STARTS_WITH' THEN 'starting'\r\n"
                + "        WHEN 'REWARDS' THEN 'covenant_reward'\r\n"
                + "        END AS relationship\r\n"
                + "UNION ALL\r\n"
                + "MATCH\r\n"
                + "        (i:Item:StartingGift)\r\n"
                + "RETURN\r\n"
                + "        i.name AS item,\r\n"
                + "        \"Starting gift\" AS source,\r\n"
                + "        \"starting_gift\" AS relationship";
        // @formatter:on
        LOGGER.debug("Query: {}", query);

        sources = new ArrayList<>();
        try (final Session session = driver.session()) {
            rows = session.run(query);

            while (rows.hasNext()) {
                record = rows.next();

                source = new DefaultItemSource();
                source.setItem(record.get("item", ""));
                source.setSource(record.get("source", ""));
                source.setRelationship(record.get("relationship", ""));
                sources.add(source);
            }
        }

        return sources;
    }

}
