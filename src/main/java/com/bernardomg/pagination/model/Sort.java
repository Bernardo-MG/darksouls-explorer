
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
