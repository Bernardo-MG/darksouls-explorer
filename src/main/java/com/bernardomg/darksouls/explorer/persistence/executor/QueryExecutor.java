
package com.bernardomg.darksouls.explorer.persistence.executor;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface QueryExecutor<Q> {

    public <T> Collection<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper);

    public <T> Collection<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters);

    public <T> PageIterable<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters, final Pagination pagination,
            final Iterable<Sort> sort);

    public <T> PageIterable<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper,
            final Pagination pagination, final Iterable<Sort> sort);

    public <T> Optional<T> fetchOne(final Q query,
            final Function<Map<String, Object>, T> mapper);

    public <T> Optional<T> fetchOne(final Q query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters);

}
