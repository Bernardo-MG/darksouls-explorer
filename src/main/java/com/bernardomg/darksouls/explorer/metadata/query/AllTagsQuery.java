
package com.bernardomg.darksouls.explorer.metadata.query;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.persistence.Query;

public final class AllTagsQuery implements Query<List<String>> {

    private final String rootTag;

    public AllTagsQuery(final String root) {
        super();

        rootTag = Objects.requireNonNull(root);
    }

    @Override
    public final List<String>
            getOutput(final Iterable<Map<String, Object>> record) {
        return StreamSupport.stream(record.spliterator(), false)
            .map(this::toTag)
            .collect(Collectors.toList());
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

    public final String toTag(final Map<String, Object> record) {
        return (String) record.getOrDefault("label", "");
    }

}
