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

import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;

public final class DslQueryExecutor
        implements QueryExecutor<BuildableStatement<ResultStatement>> {

    private final QueryExecutor<String> wrapped;

    public DslQueryExecutor(final Neo4jClient clnt) {
        super();

        wrapped = new TextQueryExecutor(clnt);
    }

    @Override
    public final <T> Collection<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        return wrapped.fetch(queryText, mapper);
    }

    @Override
    public final <T> Collection<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper,
            final Map<String, Object> parameters) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        return wrapped.fetch(queryText, mapper, parameters);
    }

    @Override
    public final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper,
            final Map<String, Object> parameters, final Pageable page) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        // TODO: This does not use the pagination info just set
        return wrapped.fetch(queryText, mapper, parameters, page);
    }

    @Override
    public final <T> Page<T> fetch(
            final BuildableStatement<ResultStatement> query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper,
            final Pageable page) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        // TODO: This does not use the pagination info just set
        return wrapped.fetch(queryText, mapper, page);
    }

    @Override
    public final Collection<Map<String, Object>> fetch(
            final BuildableStatement<ResultStatement> query,
            final Map<String, Object> parameters) {
        final String queryText;

        queryText = query.build()
            .getCypher();

        return wrapped.fetch(queryText, parameters);
    }

}
