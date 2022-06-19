
package com.bernardomg.darksouls.explorer.map.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.map.domain.ImmutableMap;
import com.bernardomg.pagination.model.Query;

public final class AllIMapsQuery
        implements Query<com.bernardomg.darksouls.explorer.map.domain.Map> {

    public AllIMapsQuery() {
        super();
    }

    @Override
    public final com.bernardomg.darksouls.explorer.map.domain.Map
            getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");

        return new ImmutableMap(id, name);
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final String query;

        query =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "  (m:Map)" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "  m.name AS name, id(m) AS id";
        // @formatter:on;

        return query;
    }

}
