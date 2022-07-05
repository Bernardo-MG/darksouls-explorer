
package com.bernardomg.pagination.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultSort implements Sort {

    @NonNull
    private final Direction direction;

    @NonNull
    private final String    property;

    private final Boolean   sorted = true;

    public DefaultSort(@NonNull final String property, @NonNull final Direction direction) {
        super();

        this.property = property;
        this.direction = direction;
    }

}
