
package com.bernardomg.pagination.model;

/**
 * Sorted data request.
 * <p>
 * Includes a flag to mark is this request is actually sorted, to ease disabled sorting.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface Sort {

    /**
     * Creates a {@code Sort} which represents disabled sorting.
     *
     * @return a disabled {@code Sort}
     */
    public static Sort disabled() {
        return DisabledSort.INSTANCE;
    }

    /**
     * Creates a {@code Sort} for the property and direction.
     *
     * @param property
     *            property to sort
     * @param direction
     *            sorting direction
     * @return a {@code Sort} for the arguments
     */
    public static Sort of(final String property, final Direction direction) {
        return new DefaultSort(property, direction);
    }

    /**
     * Direction in which the data will be sorted.
     *
     * @return the direction for sorting
     */
    public Direction getDirection();

    /**
     * Property to sort.
     *
     * @return the property to sort
     */
    public String getProperty();

    /**
     * Flags if sorting should be applied. This makes it easier disabling sorting.
     *
     * @return {@code true} if this is sorted, {@code false} otherwise
     */
    public Boolean getSorted();

}
