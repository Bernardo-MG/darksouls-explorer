
package com.bernardomg.darksouls.explorer.persistence.executor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryExecutor<Q> {

    public <T> Collection<T> fetch(final Q query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper);

    public <T> Collection<T> fetch(final Q query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper,
            final Map<String, Object> parameters);

    public <T> Page<T> fetch(final Q query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper,
            final Map<String, Object> parameters, final Pageable page);

    public <T> Page<T> fetch(final Q query,
            final Function<Iterable<Map<String, Object>>, List<T>> mapper,
            final Pageable page);

    public Collection<Map<String, Object>> fetch(final Q query,
            final Map<String, Object> parameters);

    public <T> Optional<T> fetchOne(final Q query,
            final Function<Iterable<Map<String, Object>>, T> mapper);

    public <T> Optional<T> fetchOne(final Q query,
            final Function<Iterable<Map<String, Object>>, T> mapper,
            final Map<String, Object> parameters);

    public Optional<Map<String, Object>> fetchOne(final Q query,
            final Map<String, Object> parameters);

}
