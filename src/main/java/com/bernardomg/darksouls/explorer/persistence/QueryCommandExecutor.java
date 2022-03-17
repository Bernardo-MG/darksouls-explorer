
package com.bernardomg.darksouls.explorer.persistence;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryCommandExecutor {

    public <T> Page<T> fetch(final DslQuery<Map<String, Object>, T> query,
            final Pageable page);

    public <T> Page<T> fetch(final StringQuery<Map<String, Object>, T> query,
            final Map<String, Object> parameters, final Pageable page);

    public <T> T fetchOne(
            final StringQuery<Collection<Map<String, Object>>, T> query,
            final Map<String, Object> parameters);

}
