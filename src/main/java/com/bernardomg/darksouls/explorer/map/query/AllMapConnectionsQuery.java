
package com.bernardomg.darksouls.explorer.map.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.map.domain.ImmutableMapConnection;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class AllMapConnectionsQuery
        implements Query<List<MapConnection>> {

    public AllMapConnectionsQuery() {
        super();
    }

    @Override
    public final List<MapConnection>
            getOutput(final Iterable<Map<String, Object>> record) {
        return StreamSupport.stream(record.spliterator(), false)
            .map(this::getMapConnection)
            .collect(Collectors.toList());
    }

    @Override
    public final String getStatement() {
        final String query;

        query =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "  (m:Map)-[:CONNECTS_TO]->(n:Map)" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "  ID(m) AS id, ID(n) AS connection";
        // @formatter:on;

        return query;
    }

    private final MapConnection
            getMapConnection(final Map<String, Object> record) {
        final Long id;
        final Long connection;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        connection = (Long) record.getOrDefault("connection", Long.valueOf(-1));

        return new ImmutableMapConnection(id, connection);
    }

}
