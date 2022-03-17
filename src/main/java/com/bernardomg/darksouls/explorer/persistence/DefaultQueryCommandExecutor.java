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

import java.util.Map;
import java.util.Objects;

import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public final class DefaultQueryCommandExecutor implements QueryCommandExecutor {

    private final QueryExecutor<BuildableStatement<ResultStatement>> dslExecutor;

    private final QueryExecutor<String>                              textExecutor;

    public DefaultQueryCommandExecutor(final QueryExecutor<String> textExec,
            final QueryExecutor<BuildableStatement<ResultStatement>> dslExec) {
        super();

        textExecutor = Objects.requireNonNull(textExec);
        dslExecutor = Objects.requireNonNull(dslExec);
    }

    @Override
    public final <T> Iterable<T>
            fetch(final DslQuery<Map<String, Object>, T> query) {
        return dslExecutor.fetch(query.getStatement(), query::getOutput);
    }

    @Override
    public final <T> Iterable<T> fetch(
            final DslQuery<Map<String, Object>, T> query,
            final Map<String, Object> parameters) {
        return dslExecutor.fetch(query.getStatement(), query::getOutput,
            parameters);
    }

    @Override
    public final <T> Page<T> fetch(final DslQuery<Map<String, Object>, T> query,
            final Map<String, Object> parameters, final Pageable page) {
        return dslExecutor.fetch(query.getStatement(), query::getOutput,
            parameters, page);
    }

    @Override
    public final <T> Page<T> fetch(final DslQuery<Map<String, Object>, T> query,
            final Pageable page) {
        return dslExecutor.fetch(query.getStatement(), query::getOutput, page);
    }

    @Override
    public final <T> Iterable<T>
            fetch(final TextQuery<Map<String, Object>, T> query) {
        return textExecutor.fetch(query.getStatement(), query::getOutput);
    }

    @Override
    public final <T> Iterable<T> fetch(
            final TextQuery<Map<String, Object>, T> query,
            final Map<String, Object> parameters) {
        return textExecutor.fetch(query.getStatement(), query::getOutput,
            parameters);
    }

    @Override
    public final <T> Page<T> fetch(
            final TextQuery<Map<String, Object>, T> query,
            final Map<String, Object> parameters, final Pageable page) {
        return textExecutor.fetch(query.getStatement(), query::getOutput,
            parameters, page);
    }

    @Override
    public final <T> Page<T> fetch(
            final TextQuery<Map<String, Object>, T> query,
            final Pageable page) {
        return textExecutor.fetch(query.getStatement(), query::getOutput, page);
    }

}
