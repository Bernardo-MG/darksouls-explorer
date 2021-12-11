
package com.bernardomg.darksouls.explorer.map.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultMap implements Map {

    @NonNull
    private Long   id;

    @NonNull
    private String name;

    public DefaultMap(@NonNull final Long id, @NonNull final String name) {
        super();

        this.id = id;
        this.name = name;
    }

}
