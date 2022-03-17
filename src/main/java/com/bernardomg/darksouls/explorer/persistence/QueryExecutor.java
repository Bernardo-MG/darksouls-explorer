
package com.bernardomg.darksouls.explorer.persistence;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryExecutor<Q> {

    public Collection<Map<String, Object>> fetch(final Q query);

    public <T> Collection<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper);

    public <T> Collection<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters);

    public <T> Page<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper,
            final Map<String, Object> parameters, final Pageable page);

    public <T> Page<T> fetch(final Q query,
            final Function<Map<String, Object>, T> mapper, final Pageable page);

    public Collection<Map<String, Object>> fetch(final Q query,
            final Map<String, Object> parameters);

}
