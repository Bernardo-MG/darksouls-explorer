
package com.bernardomg.darksouls.explorer.metadata.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;

@Component
public final class DefaultMetadataQueries implements MetadataQueries {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultMetadataQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final Iterable<String> getTags(final String rootTag) {
        final String query;
        final String queryTemplate;

        queryTemplate =
        // @formatter:off
              "MATCH" + System.lineSeparator()
            + "   (n:%s)" + System.lineSeparator()
            + "WITH" + System.lineSeparator()
            + "   LABELS(n) AS labels" + System.lineSeparator()
            + "UNWIND labels as label" + System.lineSeparator()
            + "RETURN" + System.lineSeparator()
            + "   DISTINCT label" + System.lineSeparator()
            + "ORDER BY" + System.lineSeparator()
            + "   label ASC";
        // @formatter:on;

        // TODO: Use parameters
        query = String.format(queryTemplate, rootTag);
        return queryExecutor.fetch(query, this::toTag);
    }

    private final String toTag(final Map<String, Object> record) {
        return (String) record.getOrDefault("label", "");
    }

}
