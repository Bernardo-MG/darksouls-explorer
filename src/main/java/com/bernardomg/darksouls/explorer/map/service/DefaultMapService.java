
package com.bernardomg.darksouls.explorer.map.service;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.map.query.AllIMapsQuery;
import com.bernardomg.darksouls.explorer.map.query.AllMapConnectionsQuery;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.persistence.executor.Query;
import com.bernardomg.persistence.executor.QueryExecutor;

@Component
public final class DefaultMapService implements MapService {

    private final QueryExecutor queryExecutor;

    public DefaultMapService(final QueryExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<Map> getAll(final Pagination pagination,
            final Sort sort) {
        final Query<Map> query;

        query = new AllIMapsQuery();

        return queryExecutor.fetch(query::getStatement, query::getOutput,
            pagination, Arrays.asList(sort));
    }

    @Override
    public final PageIterable<MapConnection>
            getAllConnections(final Pagination pagination, final Sort sort) {
        final Query<MapConnection> query;

        query = new AllMapConnectionsQuery();

        return queryExecutor.fetch(query::getStatement, query::getOutput,
            pagination, Arrays.asList(sort));
    }

}
