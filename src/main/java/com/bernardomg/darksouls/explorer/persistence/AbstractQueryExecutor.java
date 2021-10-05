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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingAndReturn;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesLimit;
import org.neo4j.cypherdsl.core.StatementBuilder.TerminalExposesSkip;
import org.neo4j.cypherdsl.core.SymbolicName;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;

public abstract class AbstractQueryExecutor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractQueryExecutor.class);

    private final Renderer      cypherRenderer;

    private final Neo4jClient   client;

    public AbstractQueryExecutor(final Neo4jClient clnt,
            final Renderer renderer) {
        super();

        client = clnt;
        cypherRenderer = renderer;
    }

    private final String getCountQuery(
            final BuildableStatement<ResultStatement> statementBuilder) {
        final String countSubquery;
        final SymbolicName name;
        final OngoingReadingAndReturn call;

        countSubquery = cypherRenderer.render(statementBuilder.build());
        name = Cypher.name("value");
        call = Cypher.call("apoc.cypher.run")
                .withArgs(Cypher.literalOf(countSubquery), Cypher.mapOf())
                .yield(name).returning(Functions.count(name));

        return cypherRenderer.render(call.build());
    }

    protected final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> statementBuilder,
            final Function<Map<String, Object>, T> mapper,
            final Pageable page) {
        final String query;
        final List<T> data;
        final ResultStatement statement;
        final Collection<Map<String, Object>> read;
        final String countQuery;
        final Long count;

        countQuery = getCountQuery(statementBuilder);

        // Pagination
        if (page != Pageable.unpaged()) {
            if (statementBuilder instanceof TerminalExposesSkip) {
                ((TerminalExposesSkip) statementBuilder)
                        .skip(page.getPageNumber());
            }
            if (statementBuilder instanceof TerminalExposesLimit) {
                ((TerminalExposesLimit) statementBuilder)
                        .limit(page.getPageSize());
            }
        }

        statement = statementBuilder.build();
        query = cypherRenderer.render(statement);

        LOGGER.debug("Query: {}", query);
        LOGGER.debug("Count: {}", countQuery);

        count = client.query(countQuery).fetchAs(Long.class).first().get();

        if (count == 0) {
            data = Collections.emptyList();
        } else {
            // Data is fetched and mapped
            read = client.query(query).fetch().all();
            data = read.stream().map(mapper).collect(Collectors.toList());
        }

        return new PageImpl<>(data, page, count);
    }

}
