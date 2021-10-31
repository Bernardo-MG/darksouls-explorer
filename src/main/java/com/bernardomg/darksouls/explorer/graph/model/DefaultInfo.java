
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.Collections;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultInfo implements Info {

    @NonNull
    private Iterable<String> description = Collections.emptyList();

    @NonNull
    private Long             id;

    @NonNull
    private String           label;

}
