
package com.bernardomg.darksouls.explorer.map.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.map.domain.ImmutableMap;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class AllIMapsQuery implements
        Query<List<com.bernardomg.darksouls.explorer.map.domain.Map>> {

    public AllIMapsQuery() {
        super();
    }

    @Override
    public final List<com.bernardomg.darksouls.explorer.map.domain.Map>
            getOutput(final Iterable<Map<String, Object>> record) {
        return StreamSupport.stream(record.spliterator(), false)
            .map(this::toMap)
            .collect(Collectors.toList());
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

    private final com.bernardomg.darksouls.explorer.map.domain.Map
            toMap(final Map<String, Object> record) {
        final Long id;
        final String name;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");

        return new ImmutableMap(id, name);
    }

}
