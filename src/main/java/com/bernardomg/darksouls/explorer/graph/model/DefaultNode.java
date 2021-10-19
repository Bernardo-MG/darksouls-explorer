
package com.bernardomg.darksouls.explorer.graph.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultNode implements Node {

    @NonNull
    private Long   id;

    @NonNull
    private String name;

}
