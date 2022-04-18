
package com.bernardomg.darksouls.explorer.persistence.model;

import lombok.Data;

@Data
public final class DisabledSort implements Sort {

    private final Direction direction = Direction.ASC;

    private final String    property  = "";

    @Override
    public final Boolean isSorted() {
        return false;
    }

}