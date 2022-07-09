
package com.bernardomg.pagination.model;

/**
 * Paginated data request.
 * <p>
 * Includes a flag to mark is this request is actually paged, to ease disabled pagination.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface Pagination {

    /**
     * Page index to read.
     * 
     * @return the page index
     */
    public Integer getPage();

    /**
     * Flags if pagination should be applied. This makes it easier disabling pagination.
     * 
     * @return {@code true} if this is paged, {@code false} otherwise
     */
    public Boolean getPaged();

    /**
     * Number of elements to read per page.
     * 
     * @return the page size
     */
    public Integer getSize();

}
