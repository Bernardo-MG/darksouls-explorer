
package com.bernardomg.darksouls.explorer.map.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.map.query.AllIMapConnectionsQuery;
import com.bernardomg.darksouls.explorer.map.query.AllIMapsQuery;
import com.bernardomg.darksouls.explorer.persistence.QueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.TextQuery;

@Component
public final class DefaultMapService implements MapService {

    private final QueryCommandExecutor queryExecutor;

    public DefaultMapService(final QueryCommandExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Page<Map> getAll(final Pageable page) {
        final TextQuery<java.util.Map<String, Object>, com.bernardomg.darksouls.explorer.map.domain.Map> query;

        query = new AllIMapsQuery();

        return queryExecutor.fetch(query, page);
    }

    @Override
    public final Page<MapConnection> getAllConnections(final Pageable page) {
        final TextQuery<java.util.Map<String, Object>, MapConnection> query;

        query = new AllIMapConnectionsQuery();

        return queryExecutor.fetch(query, page);
    }

}
