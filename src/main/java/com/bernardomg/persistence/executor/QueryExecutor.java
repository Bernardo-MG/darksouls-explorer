
package com.bernardomg.persistence.executor;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface QueryExecutor {

    public <T> Collection<T> fetch(
            final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper);

    public <T> Collection<T> fetch(
            final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters);

    public <T> PageIterable<T> fetch(
            final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters, final Pagination pagination,
            final Iterable<Sort> sort);

    public <T> PageIterable<T> fetch(
            final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper,
            final Pagination pagination, final Iterable<Sort> sort);

    public <T> Optional<T> fetchOne(
            final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper);

    public <T> Optional<T> fetchOne(
            final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters);

}
