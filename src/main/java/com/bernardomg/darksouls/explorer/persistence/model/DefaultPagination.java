
package com.bernardomg.darksouls.explorer.persistence.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultPagination implements Pagination {

    @NonNull
    private final Integer page;

    @NonNull
    private final Integer size;

    public DefaultPagination(@NonNull final Integer page,
            @NonNull final Integer size) {
        super();

        this.page = page;
        this.size = size;
    }

    @Override
    public final Boolean isPaged() {
        return true;
    }

}
