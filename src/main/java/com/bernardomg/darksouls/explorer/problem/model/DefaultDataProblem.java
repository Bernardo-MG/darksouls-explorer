
package com.bernardomg.darksouls.explorer.problem.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class DefaultDataProblem implements DataProblem {

    @NonNull
    private String id;

    @NonNull
    private String source;

    @NonNull
    private String problem;

    public DefaultDataProblem(@NonNull String id, @NonNull String source,
            @NonNull String problem) {
        super();

        this.id = id;
        this.source = source;
        this.problem = problem;
    }

}
