
package com.bernardomg.darksouls.explorer.map.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.map.domain.ImmutableMap;
import com.bernardomg.darksouls.explorer.persistence.TextQuery;

public final class AllIMapsQuery implements
        TextQuery<Map<String, Object>, com.bernardomg.darksouls.explorer.map.domain.Map> {

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
    public final String getStatement() {
        final String query;

        query =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "  (m:Map)" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "  m.name AS name, ID(m) AS id";
        // @formatter:on;

        return query;
    }

}
