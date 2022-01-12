
package com.bernardomg.darksouls.explorer.map.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableMap implements Map {

    @NonNull
    private final Long   id;

    @NonNull
    private final String name;

    public ImmutableMap(@NonNull final Long id, @NonNull final String name) {
        super();

        this.id = id;
        this.name = name;
    }

}
