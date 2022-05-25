
package com.bernardomg.darksouls.explorer.persistence.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DisabledPagination implements Pagination {

    @NonNull
    private final Integer page  = -1;

    @NonNull
    private final Boolean paged = false;

    @NonNull
    private final Integer size  = -1;

}
