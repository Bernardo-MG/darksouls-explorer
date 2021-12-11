
package com.bernardomg.darksouls.explorer.map.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableMapConnection implements MapConnection {

    @NonNull
    private final Long connection;

    @NonNull
    private final Long id;

    public ImmutableMapConnection(@NonNull final Long id,
            @NonNull final Long connection) {
        super();

        this.id = id;
        this.connection = connection;
    }

}
