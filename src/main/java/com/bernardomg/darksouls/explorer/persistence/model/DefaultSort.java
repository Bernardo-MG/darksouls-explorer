
package com.bernardomg.darksouls.explorer.persistence.model;

import lombok.Data;

@Data
public final class DefaultSort implements Sort {

    private String    property;

    private Direction direction;

    @Override
    public final Boolean isSorted() {
        return true;
    }

}
