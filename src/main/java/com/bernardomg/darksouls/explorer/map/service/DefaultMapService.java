
package com.bernardomg.darksouls.explorer.map.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.map.query.AllIMapsQuery;
import com.bernardomg.darksouls.explorer.map.query.AllMapConnectionsQuery;
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
        final TextQuery<List<Map>> query;

        query = new AllIMapsQuery();

        return queryExecutor.fetch(query, page);
    }

    @Override
    public final Page<MapConnection> getAllConnections(final Pageable page) {
        final TextQuery<List<MapConnection>> query;

        query = new AllMapConnectionsQuery();

        return queryExecutor.fetch(query, page);
    }

}
