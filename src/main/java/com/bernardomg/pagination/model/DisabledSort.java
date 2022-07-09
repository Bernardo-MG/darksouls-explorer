
package com.bernardomg.pagination.model;

import lombok.Data;

/**
 * Disabled sorted data request. This serves as a null object to disable sorting.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class DisabledSort implements Sort {

    /**
     * Default direction.
     */
    private final Direction direction = Direction.ASC;

    /**
     * Default property.
     */
    private final String    property  = "";

    /**
     * Disabled sort flag.
     */
    private final Boolean   sorted    = false;

}
