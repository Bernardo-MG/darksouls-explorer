
package com.bernardomg.pagination.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Default implementation of the sorted data request.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class DefaultSort implements Sort {

    /**
     * Direction in which the data will be sorted.
     */
    @NonNull
    private final Direction direction;

    /**
     * Property to sort.
     */
    @NonNull
    private final String    property;

    /**
     * Always sorted.
     */
    private final Boolean   sorted = true;

    /**
     * Builds a sort request with the specified data.
     *
     * @param prop
     *            property to sort
     * @param dir
     *            sort direction
     */
    public DefaultSort(@NonNull final String prop, @NonNull final Direction dir) {
        super();

        property = prop;
        direction = dir;
    }

}
