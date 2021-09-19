/**
 * Copyright 2020 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.darksouls.explorer.graph.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bernardomg.darksouls.explorer.graph.model.DefaultGraph;
import com.bernardomg.darksouls.explorer.graph.model.DefaultItem;
import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Item;
import com.bernardomg.darksouls.explorer.graph.query.processor.GraphProcessor;
import com.bernardomg.darksouls.explorer.graph.query.processor.Processor;

import graphql.com.google.common.base.Preconditions;
import graphql.com.google.common.collect.Iterables;

/**
 * People repository.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Repository
public class DefaultGraphQueries implements GraphQueries {

    /**
     * Logger.
     */
    private static final Logger    LOGGER         = LoggerFactory
            .getLogger(DefaultGraphQueries.class);

    private final Driver           driver;

    private final Processor<Graph> graphProcessor = new GraphProcessor();

    @Autowired
    public DefaultGraphQueries(final Driver drv) {
        super();

        driver = drv;
    }

    @Override
    public final Graph findAll() {
        final Result rows;
        final String query;
        final Graph result;

        query = "MATCH (s)-[r]->(t) RETURN s.name AS source, ID(s) AS sourceId, t.name AS target, ID(t) AS targetId, type(r) AS relationship";
        LOGGER.debug("Query: {}", query);

        try (final Session session = driver.session()) {
            rows = session.run(query);
            result = graphProcessor.process(rows);
        }

        return result;
    }

    @Override
    public final Graph findAllByLinkType(final Iterable<String> types) {
        final Result rows;
        final String queryTemplate;
        final String query;
        final Graph result;
        final Iterable<String> validTypes;

        Preconditions.checkNotNull(types);

        LOGGER.debug("Types: {}", types);

        if (Iterables.isEmpty(types)) {
            result = new DefaultGraph();
        } else {
            validTypes = StreamSupport.stream(types.spliterator(), false)
                    .map((string) -> "'" + string + "'")
                    .collect(Collectors.toList());

            queryTemplate = "MATCH (s)-[r]->(t) WHERE type(r) IN %s RETURN s.name AS source, ID(s) AS sourceId, t.name AS target, ID(t) AS targetId, type(r) AS relationship";
            query = String.format(queryTemplate, validTypes);
            LOGGER.debug("Query: {}", query);

            try (final Session session = driver.session()) {
                rows = session.run(query);
                result = graphProcessor.process(rows);
            }
        }

        return result;
    }

    @Override
    public final Optional<Item> findById(final Integer id) {
        final Result rows;
        final Record row;
        final String queryTemplate;
        final String query;
        final Item node;
        final Optional<Item> result;
        final String description;

        Preconditions.checkNotNull(id);

        LOGGER.debug("Id: {}", id);

        queryTemplate = "MATCH (node) WHERE ID(node) = %s RETURN node.name AS name, node.description AS description, ID(node) AS id";
        query = String.format(queryTemplate, id);
        LOGGER.debug("Query: {}", query);

        final Session session;

        session = driver.session();

        rows = session.run(query);

        if (rows.hasNext()) {
            row = rows.single();
            node = new DefaultItem();
            node.setId(row.get("id", 0l));
            node.setName(row.get("name", ""));

            description = row.get("description", "");
            if (!description.isBlank()) {
                node.setDescription(
                        Arrays.asList(row.get("description", "").split("\\|")));
            } else {
                node.setDescription(Collections.emptyList());
            }

            result = Optional.of(node);

            LOGGER.debug("Result: {}", node);
        } else {
            result = Optional.empty();

            LOGGER.debug("No data found");
        }

        session.close();

        return result;
    }

}
