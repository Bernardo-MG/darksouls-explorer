
package com.bernardomg.darksouls.explorer.map.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.map.domain.ImmutableMapConnection;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.persistence.TextQuery;

public final class AllIMapConnectionsQuery
        implements TextQuery<Map<String, Object>, MapConnection> {

    public AllIMapConnectionsQuery() {
        super();
    }

    @Override
    public final MapConnection getOutput(final Map<String, Object> record) {
        final Long id;
        final Long connection;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        connection = (Long) record.getOrDefault("connection", Long.valueOf(-1));

        return new ImmutableMapConnection(id, connection);
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

}
