
package com.bernardomg.darksouls.explorer.item.query;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.query.processor.GraphProcessor;
import com.bernardomg.darksouls.explorer.graph.query.processor.Processor;

public final class DefaultItemQueries implements ItemQueries {

    /**
     * Logger.
     */
    private static final Logger    LOGGER         = LoggerFactory
            .getLogger(DefaultItemQueries.class);

    private final Driver           driver;

    private final Processor<Graph> graphProcessor = new GraphProcessor();

    public DefaultItemQueries(final Driver drv) {
        super();

        driver = drv;
    }

    @Override
    public final Graph getSources(final String name) {
        final Result rows;
        final String query;
        final Graph result;
        final Map<String, Object> parameters;

        parameters = new HashMap<>();
        parameters.put("item", name);

        query = "MATCH (s)-[rel:DROPS|SELLS]->(i:Item), (s)-[:LOCATED_IN]->(l) WHERE i.name = $item RETURN i.name, s.name, l.name, rel.price";
        LOGGER.debug("Query: {}", query);

        try (final Session session = driver.session()) {
            rows = session.run(query, parameters);
            result = graphProcessor.process(rows);
        }

        return result;
    }

}
