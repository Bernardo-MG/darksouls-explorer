
package com.bernardomg.darksouls.explorer.problems.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class DefaultDataProblem implements DataProblem {

    @NonNull
    private String id;

}
