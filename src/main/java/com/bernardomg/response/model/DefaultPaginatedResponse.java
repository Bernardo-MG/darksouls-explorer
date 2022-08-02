
package com.bernardomg.response.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Default implementation of the paginated response.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 * @param <T>
 *            response content type
 */
@Data
public final class DefaultPaginatedResponse<T> implements PaginatedResponse<T> {

    /**
     * Response content.
     */
    @NonNull
    private T       content;

    /**
     * Number of elements in the page.
     */
    private Integer elementsInPage = -1;

    /**
     * Flags this is as the first page.
     */
    private Boolean first          = false;

    /**
     * Flags this is as the last page.
     */
    private Boolean last           = false;

    /**
     * Number of this page.
     */
    private Integer pageNumber     = -1;

    /**
     * Size of this page.
     */
    private Integer size           = -1;

    /**
     * Total number of elements among all the pages.
     */
    private Long    totalElements  = -1L;

    /**
     * Total number of pages.
     */
    private Integer totalPages     = -1;

    /**
     * Default constructor.
     */
    public DefaultPaginatedResponse() {
        super();
    }

    /**
     * Constructs a response with the specified content.
     *
     * @param cont
     *            content
     */
    public DefaultPaginatedResponse(@NonNull final T cont) {
        super();

        content = cont;
    }

}
