
package com.bernardomg.pagination.model;

import lombok.Data;

@Data
public final class DisabledSort implements Sort {

    private final Direction direction = Direction.ASC;

    private final String    property  = "";

    private final Boolean   sorted    = false;

}
