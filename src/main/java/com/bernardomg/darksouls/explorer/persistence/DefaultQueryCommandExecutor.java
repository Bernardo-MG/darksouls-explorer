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
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public final class DefaultQueryCommandExecutor implements QueryCommandExecutor {

    private final QueryExecutor    executor;

    private final DslQueryExecutor dslExecutor;

    public DefaultQueryCommandExecutor(final QueryExecutor exec,
            final DslQueryExecutor dslExec) {
        super();

        executor = Objects.requireNonNull(exec);
        dslExecutor = Objects.requireNonNull(dslExec);
    }

    @Override
    public final <T> Page<T> fetch(final DslQuery<Map<String, Object>, T> query,
            final Pageable page) {
        return dslExecutor.fetch(query.getStatement(), query::getOutput, page);
    }

    @Override
    public final <T> Page<T> fetch(
            final StringQuery<Map<String, Object>, T> query,
            final Map<String, Object> parameters, final Pageable page) {
        return executor.fetch(query.getStatement(), query::getOutput,
            parameters, page);
    }

    @Override
    public final <T> T fetchOne(
            final StringQuery<Collection<Map<String, Object>>, T> query,
            final Map<String, Object> parameters) {
        final Collection<Map<String, Object>> read;

        read = executor.fetch(query.getStatement(), parameters);

        return query.getOutput(read);
    }

}
