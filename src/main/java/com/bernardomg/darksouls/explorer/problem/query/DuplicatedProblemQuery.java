
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.model.ImmutableDataProblem;

public final class DuplicatedProblemQuery implements Query<DataProblem> {

    private final String error = "duplicated";

    private final String source;

    public DuplicatedProblemQuery(final String source) {
        super();

        this.source = source;
    }

    @Override
    public final DataProblem getOutput(final Map<String, Object> record) {
        return new ImmutableDataProblem((String) record.getOrDefault("id", ""),
            source, error);
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "  (n)" + System.lineSeparator()
        + "WHERE" + System.lineSeparator()
        + "  $node IN LABELS(n)" + System.lineSeparator()
        + "WITH" + System.lineSeparator()
        + "  n.name AS id," + System.lineSeparator()
        + "  count(n) AS count" + System.lineSeparator()
        + "WHERE" + System.lineSeparator()
        + "  count > 1 RETURN id";
        // @formatter:on

        return query;
    }

}
