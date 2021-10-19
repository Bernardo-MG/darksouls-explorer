/**
 * Copyright 2021 the original author or authors
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Relationship;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.graph.model.DefaultGraph;
import com.bernardomg.darksouls.explorer.graph.model.DefaultInfo;
import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Info;
import com.bernardomg.darksouls.explorer.graph.query.processor.GraphProcessor;
import com.bernardomg.darksouls.explorer.graph.query.processor.Processor;

import lombok.NonNull;

/**
 * People repository.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Component
public final class DefaultGraphQueries implements GraphQueries {

    /**
     * Logger.
     */
    private static final Logger    LOGGER         = LoggerFactory
            .getLogger(DefaultGraphQueries.class);

    private final Neo4jClient      client;

    private final Renderer         cypherRenderer = Renderer
            .getDefaultRenderer();

    private final Processor<Graph> graphProcessor = new GraphProcessor();

    @Autowired
    public DefaultGraphQueries(final Neo4jClient clnt) {
        super();

        client = clnt;
    }

    @Override
    public final Graph findAll() {
        final Node source;
        final Node target;
        final Relationship rel;
        final BuildableStatement<ResultStatement> statementBuilder;
        final String query;
        final Statement statement;
        final Collection<Map<String, Object>> rows;

        source = Cypher.anyNode().named("s");
        target = Cypher.anyNode().named("t");
        rel = source.relationshipTo(target).named("r");
        statementBuilder = Cypher.match(rel).returning(
                source.property("name").as("source"),
                source.internalId().as("sourceId"),
                target.property("name").as("target"),
                target.internalId().as("targetId"),
                Functions.type(rel).as("relationship"));

        statement = statementBuilder.build();
        query = cypherRenderer.render(statement);

        LOGGER.debug("Query: {}", query);

        rows = client.query(query).fetch().all();
        return graphProcessor.process(rows);
    }

    @Override
    public final Graph
            findAllByLinkType(@NonNull final Iterable<String> types) {
        final String query;
        final Graph result;
        final Collection<Expression> validTypes;
        final Collection<Map<String, Object>> rows;
        final Node source;
        final Node target;
        final Relationship rel;
        final BuildableStatement<ResultStatement> statementBuilder;
        final Statement statement;

        LOGGER.debug("Filtering by links: {}", types);

        if (IterableUtils.isEmpty(types)) {
            result = new DefaultGraph();
        } else {
            validTypes = StreamSupport.stream(types.spliterator(), false)
                    .map(Cypher::literalOf).collect(Collectors.toList());

            source = Cypher.anyNode().named("s");
            target = Cypher.anyNode().named("t");
            rel = source.relationshipTo(target).named("r");
            statementBuilder = Cypher.match(rel)
                    .where(Functions.type(rel).in(Cypher.listOf(validTypes)))
                    .returning(source.property("name").as("source"),
                            source.internalId().as("sourceId"),
                            target.property("name").as("target"),
                            target.internalId().as("targetId"),
                            Functions.type(rel).as("relationship"));

            statement = statementBuilder.build();
            query = cypherRenderer.render(statement);

            LOGGER.debug("Query: {}", query);

            rows = client.query(query).fetch().all();
            result = graphProcessor.process(rows);
        }

        return result;
    }

    @Override
    public final Iterable<String> findAllLinks() {
        final String query;
        final Collection<String> result;
        final Collection<Map<String, Object>> rows;
        final Node source;
        final Node target;
        final Relationship rel;
        final AliasedExpression relField;
        final BuildableStatement<ResultStatement> statementBuilder;
        final Statement statement;

        source = Cypher.anyNode().named("s");
        target = Cypher.anyNode().named("t");
        rel = source.relationshipTo(target).named("r");
        relField = Functions.type(rel).as("relationship");
        statementBuilder = Cypher.match(rel).returningDistinct(relField)
                .orderBy(relField.asName().ascending());

        statement = statementBuilder.build();
        query = cypherRenderer.render(statement);

        LOGGER.debug("Query: {}", query);

        result = new ArrayList<>();
        rows = client.query(query).fetch().all();
        for (final Map<String, Object> row : rows) {
            result.add((String) row.getOrDefault("relationship", ""));
        }

        return result;
    }

    @Override
    public final Optional<Info> findById(@NonNull final Long id) {
        final String query;
        final Info info;
        final Optional<Info> result;
        final String description;
        final Map<String, Object> row;
        final Optional<Map<String, Object>> read;
        final BuildableStatement<ResultStatement> statementBuilder;
        final Node node;
        final Statement statement;

        LOGGER.debug("Id: {}", id);

        node = Cypher.anyNode().named("node");
        statementBuilder = Cypher.match(node)
                .where(node.internalId().isEqualTo(Cypher.literalOf(id)))
                .returning(node.property("name").as("name"),
                        node.property("description").as("description"),
                        node.internalId().as("id"));

        statement = statementBuilder.build();
        query = cypherRenderer.render(statement);
        LOGGER.debug("Query: {}", query);

        read = client.query(query).fetch().first();

        if (read.isEmpty()) {
            result = Optional.empty();

            LOGGER.debug("No data found");
        } else {
            row = read.get();
            info = new DefaultInfo((Long) row.getOrDefault("id", 0l),
                    (String) row.getOrDefault("name", ""));

            description = (String) row.getOrDefault("description", "");
            if (StringUtils.isBlank(description)) {
                info.setDescription(Collections.emptyList());
            } else {
                info.setDescription(Arrays
                        .asList(((String) row.getOrDefault("description", ""))
                                .split("\\|")));
            }

            result = Optional.of(info);

            LOGGER.debug("Result: {}", info);
        }

        return result;
    }

}
