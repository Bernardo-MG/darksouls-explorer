
package com.bernardomg.darksouls.explorer.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryCommandExecutor {

    public <T> Iterable<T> fetch(final Query<List<T>> query);

    public <T> Iterable<T> fetch(final Query<List<T>> query,
            final Map<String, Object> parameters);

    public <T> Page<T> fetch(final Query<List<T>> query,
            final Map<String, Object> parameters, final Pageable page);

    public <T> Page<T> fetch(final Query<List<T>> query, final Pageable page);

}
