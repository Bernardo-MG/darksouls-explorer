
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.model.ImmutableDataProblem;
import com.bernardomg.persistence.executor.Query;

public final class MissingFieldQuery implements Query<DataProblem> {

    private final String error = "missing_field";

    private final String field;

    private final String source;

    public MissingFieldQuery(final String source, final String field) {
        super();

        this.source = source;
        this.field = field;
    }

    @Override
    public final DataProblem getOutput(final Map<String, Object> record) {
        return new ImmutableDataProblem((String) record.getOrDefault("id", ""), source, error);
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final String template;

        template =
        // @formatter:off
                "MATCH" + System.lineSeparator()
                + "  (n) " + System.lineSeparator()
                + "WHERE" + System.lineSeparator()
                + "  $node IN LABELS(n) " + System.lineSeparator()
                + "  AND (n.%1$s = '' OR n.%1$s IS NULL) " + System.lineSeparator()
                + "RETURN" + System.lineSeparator()
                + "  n.name AS id";
        return String.format(template, field);
    }

}
