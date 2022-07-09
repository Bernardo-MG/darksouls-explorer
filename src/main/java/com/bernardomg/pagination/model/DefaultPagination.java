
package com.bernardomg.pagination.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultPagination implements Pagination {

    public static final Integer DEFAULT_SIZE = 20;

    @NonNull
    private final Integer       page;

    @NonNull
    private final Boolean       paged        = true;

    @NonNull
    private final Integer       size;

    public DefaultPagination(@NonNull final Integer pg) {
        super();

        page = pg;
        size = DEFAULT_SIZE;
    }

    public DefaultPagination(@NonNull final Integer pg, @NonNull final Integer sz) {
        super();

        page = pg;
        size = sz;
    }

}
