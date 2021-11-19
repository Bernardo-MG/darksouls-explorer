
package com.bernardomg.darksouls.explorer.problem.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class DefaultDataProblem implements DataProblem {

    @NonNull
    private String id;

    @NonNull
    private String problem;

    @NonNull
    private String source;

    public DefaultDataProblem(@NonNull final String id,
            @NonNull final String source, @NonNull final String problem) {
        super();

        this.id = id;
        this.source = source;
        this.problem = problem;
    }

}
