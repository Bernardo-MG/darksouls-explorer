
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
     * Default page size.
     */
    Integer DEFAULT_SIZE = 20;

    /**
     * Creates a {@code Pagination} which represents disabled pagination.
     *
     * @return a disabled {@code Pagination}
     */
    public static Pagination disabled() {
        return DisabledPagination.INSTANCE;
    }

    /**
     * Creates a {@code Pagination} which represents the first page.
     *
     * @return a {@code Pagination} for the first page
     */
    public static Pagination first() {
        return of(0);
    }

    /**
     * Creates a {@code Pagination} for the received page and default size.
     *
     * @param page
     *            zero-based page number
     * @return {@code Pagination} for the page and size
     */
    public static Pagination of(final Integer page) {
        return new ImmutablePagination(page);
    }

    /**
     * Creates a {@code Pagination} for the received page and size.
     *
     * @param page
     *            zero-based page number
     * @param size
     *            page size
     * @return {@code Pagination} for the page and size
     */
    public static Pagination of(final Integer page, final Integer size) {
        return new ImmutablePagination(page, size);
    }

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
