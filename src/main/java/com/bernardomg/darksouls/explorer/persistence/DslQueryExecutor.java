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

package com.bernardomg.darksouls.explorer.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingAndReturn;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesLimit;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesOrderBy;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesSkip;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalOngoingOrderDefinition;
import org.neo4j.cypherdsl.core.SymbolicName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.support.PageableExecutionUtils;

public final class DslQueryExecutor
        implements QueryExecutor<BuildableStatement<ResultStatement>> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(DslQueryExecutor.class);

    private final Neo4jClient   client;

    public DslQueryExecutor(final Neo4jClient clnt) {
        super();

        client = Objects.requireNonNull(clnt);
    }

    @Override
    public final Collection<Map<String, Object>>
            fetch(final BuildableStatement<ResultStatement> query) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        LOGGER.debug("Query:\n{}", query);

        // Data is fetched and mapped
        return client.query(queryText)
            .fetch()
            .all();
    }

    @Override
    public final <T> Collection<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Map<String, Object>, T> mapper) {
        final String queryText;
        final Collection<Map<String, Object>> read;

        queryText = query.build()
            .getCypher();

        LOGGER.debug("Query:\n{}", queryText);

        // Data is fetched and mapped
        read = client.query(queryText)
            .fetch()
            .all();

        return read.stream()
            .map(mapper)
            .collect(Collectors.toList());
    }

    @Override
    public final <T> Collection<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters) {
        final String queryText;
        final Collection<Map<String, Object>> read;

        queryText = query.build()
            .getCypher();

        LOGGER.debug("Query:\n{}", queryText);

        // Data is fetched and mapped
        read = client.query(queryText)
            .bindAll(parameters)
            .fetch()
            .all();
        return read.stream()
            .map(mapper)
            .collect(Collectors.toList());
    }

    @Override
    public final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters, final Pageable page) {
        final String queryText;
        final List<T> data;
        final Collection<Map<String, Object>> read;
        final Statement baseStatement;
        TerminalOngoingOrderDefinition orderExpression;

        baseStatement = query.build();

        // Pagination
        if (page.isPaged()) {
            if (query instanceof TerminalExposesSkip) {
                ((TerminalExposesSkip) query)
                    .skip(page.getPageNumber() * page.getPageSize());
            }
            if (query instanceof TerminalExposesLimit) {
                ((TerminalExposesLimit) query).limit(page.getPageSize());
            }
        }

        // Sort
        if (page.getSort()
            .isSorted()) {
            if (query instanceof TerminalExposesOrderBy) {
                for (final Order order : page.getSort()) {
                    orderExpression = ((TerminalExposesOrderBy) query)
                        .orderBy(Cypher.anyNode()
                            .property("property")
                            .as(order.getProperty())
                            .asName());
                    if (order.isAscending()) {
                        orderExpression.ascending();
                    } else {
                        orderExpression.descending();
                    }
                }
            }
        }

        queryText = query.build()
            .getCypher();

        LOGGER.debug("Query:\n{}", queryText);

        // Data is fetched and mapped
        read = client.query(queryText)
            .bindAll(parameters)
            .fetch()
            .all();
        data = read.stream()
            .map(mapper)
            .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(data, page,
            () -> count(baseStatement));
    }

    @Override
    public final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Map<String, Object>, T> mapper,
            final Pageable page) {
        final String queryText;
        final List<T> data;
        final Collection<Map<String, Object>> read;
        final Statement baseStatement;
        TerminalOngoingOrderDefinition orderExpression;

        baseStatement = query.build();

        // Pagination
        if (page.isPaged()) {
            if (query instanceof TerminalExposesSkip) {
                ((TerminalExposesSkip) query)
                    .skip(page.getPageNumber() * page.getPageSize());
            }
            if (query instanceof TerminalExposesLimit) {
                ((TerminalExposesLimit) query).limit(page.getPageSize());
            }
        }

        // Sort
        if (page.getSort()
            .isSorted()) {
            if (query instanceof TerminalExposesOrderBy) {
                for (final Order order : page.getSort()) {
                    orderExpression = ((TerminalExposesOrderBy) query)
                        .orderBy(Cypher.anyNode()
                            .property("property")
                            .as(order.getProperty())
                            .asName());
                    if (order.isAscending()) {
                        orderExpression.ascending();
                    } else {
                        orderExpression.descending();
                    }
                }
            }
        }

        queryText = query.build()
            .getCypher();

        LOGGER.debug("Query:\n{}", queryText);

        // Data is fetched and mapped
        read = client.query(queryText)
            .fetch()
            .all();
        data = read.stream()
            .map(mapper)
            .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(data, page,
            () -> count(baseStatement));
    }

    @Override
    public final Collection<Map<String, Object>> fetch(
            final BuildableStatement<ResultStatement> query,
            final Map<String, Object> parameters) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        LOGGER.debug("Query:\n{}", queryText);

        // Data is fetched and mapped
        return client.query(queryText)
            .bindAll(parameters)
            .fetch()
            .all();
    }

    private final Long count(final Statement statement) {
        final String countQuery;

        countQuery = getCountQuery(statement.getCypher());

        LOGGER.debug("Count: {}", countQuery);

        return client.query(countQuery)
            .fetchAs(Long.class)
            .first()
            .get();
    }

    private final String getCountQuery(final String subquery) {
        final SymbolicName name;
        final OngoingReadingAndReturn call;

        name = Cypher.name("value");
        call = Cypher.call("apoc.cypher.run")
            .withArgs(Cypher.literalOf(subquery), Cypher.mapOf())
            .yield(name)
            .returning(Functions.count(name));

        return call.build()
            .getCypher();
    }

}
