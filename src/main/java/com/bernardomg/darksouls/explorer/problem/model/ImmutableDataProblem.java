
package com.bernardomg.darksouls.explorer.problem.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableDataProblem implements DataProblem {

    @NonNull
    private final String name;

    @NonNull
    private final String problem;

    @NonNull
    private final String source;

    public ImmutableDataProblem(@NonNull final String name, @NonNull final String source,
            @NonNull final String problem) {
        super();

        this.name = name;
        this.source = source;
        this.problem = problem;
    }

}
