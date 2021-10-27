
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.Collections;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultGraph implements Graph {

    @NonNull
    private Iterable<Link>   links = Collections.emptyList();

    @NonNull
    private Iterable<Node>   nodes = Collections.emptyList();

    @NonNull
    private Iterable<String> types = Collections.emptyList();

}
