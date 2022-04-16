
package com.bernardomg.darksouls.explorer.persistence.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultSort implements Sort {

    @NonNull
    private final Direction direction;

    @NonNull
    private final String    property;

    public DefaultSort(@NonNull final String property,
            @NonNull final Direction direction) {
        super();

        this.property = property;
        this.direction = direction;
    }

    @Override
    public final Boolean isSorted() {
        return true;
    }

}
