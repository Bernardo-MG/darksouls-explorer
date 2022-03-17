
package com.bernardomg.darksouls.explorer.metadata.query;

import java.util.Map;
import java.util.Objects;

import com.bernardomg.darksouls.explorer.persistence.TextQuery;

public final class AllTagsQuery
        implements TextQuery<Map<String, Object>, String> {

    private final String rootTag;

    public AllTagsQuery(final String root) {
        super();

        rootTag = Objects.requireNonNull(root);
    }

    @Override
    public final String getOutput(final Map<String, Object> record) {
        return (String) record.getOrDefault("label", "");
    }

    @Override
    public final String getStatement() {
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

        return query;
    }

}
