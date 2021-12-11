
package com.bernardomg.darksouls.explorer.map.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.map.domain.ImmutableMap;
import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;

@Component
public final class DefaultMapQueries implements MapQueries {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultMapQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final Page<Map> findAll(final Pageable page) {
        final String query;

        query =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "  (m:Map)" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "  m.name AS name, ID(m) AS id";
        // @formatter:on;

        return queryExecutor.fetch(query, this::toMap, page);
    }

    private final Map toMap(final java.util.Map<String, Object> record) {
        final Long id;
        final String name;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");

        return new ImmutableMap(id, name);
    }

}
