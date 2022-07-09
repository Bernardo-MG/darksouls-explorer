
package com.bernardomg.pagination.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Disabled paginated data request. This serves as a null object to disable pagination.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class DisabledPagination implements Pagination {

    /**
     * Default page.
     */
    @NonNull
    private final Integer page  = -1;

    /**
     * Disabled pagination flag.
     */
    @NonNull
    private final Boolean paged = false;

    /**
     * Default size.
     */
    @NonNull
    private final Integer size  = -1;

}
