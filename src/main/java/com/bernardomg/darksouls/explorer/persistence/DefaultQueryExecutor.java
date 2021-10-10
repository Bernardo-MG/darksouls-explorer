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
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.support.PageableExecutionUtils;

public final class DefaultQueryExecutor implements QueryExecutor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultQueryExecutor.class);

    private final Renderer      cypherRenderer;

    private final Neo4jClient   client;

    public DefaultQueryExecutor(final Neo4jClient clnt,
            final Renderer renderer) {
        super();

        client = clnt;
        cypherRenderer = renderer;
    }

    @Override
    public final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> statementBuilder,
            final Function<Map<String, Object>, T> mapper,
            final Pageable page) {
        final String query;
        final List<T> data;
        final ResultStatement statement;
        final Collection<Map<String, Object>> read;
        final Statement baseStatement;
        TerminalOngoingOrderDefinition orderExpression;

        baseStatement = statementBuilder.build();

        // Pagination
        if (page.isPaged()) {
            if (statementBuilder instanceof TerminalExposesSkip) {
                ((TerminalExposesSkip) statementBuilder)
                        .skip(page.getPageNumber() * page.getPageSize());
            }
            if (statementBuilder instanceof TerminalExposesLimit) {
                ((TerminalExposesLimit) statementBuilder)
                        .limit(page.getPageSize());
            }
        }

        // Sort
        // TODO: Apply sort
        if (page.getSort().isSorted()) {
            if (statementBuilder instanceof TerminalExposesOrderBy) {
                for (final Order order : page.getSort()) {
                    orderExpression = ((TerminalExposesOrderBy) statementBuilder)
                            .orderBy(Cypher.anyNode().property("property")
                                    .as(order.getProperty()).asName());
                    if (order.isAscending()) {
                        orderExpression.ascending();
                    } else {
                        orderExpression.descending();
                    }
                }
            }
        }

        statement = statementBuilder.build();
        query = cypherRenderer.render(statement);

        LOGGER.debug("Query: {}", query);

        // Data is fetched and mapped
        read = client.query(query).fetch().all();
        data = read.stream().map(mapper).collect(Collectors.toList());

        return PageableExecutionUtils.getPage(data, page,
                () -> count(baseStatement));
    }

    private final Long count(final Statement statement) {
        final String countQuery;

        countQuery = getCountQuery(statement);

        LOGGER.debug("Count: {}", countQuery);

        return client.query(countQuery).fetchAs(Long.class).first().get();
    }

    private final String getCountQuery(final Statement statement) {
        final String countSubquery;
        final SymbolicName name;
        final OngoingReadingAndReturn call;

        countSubquery = cypherRenderer.render(statement);
        name = Cypher.name("value");
        call = Cypher.call("apoc.cypher.run")
                .withArgs(Cypher.literalOf(countSubquery), Cypher.mapOf())
                .yield(name).returning(Functions.count(name));

        return cypherRenderer.render(call.build());
    }

}
