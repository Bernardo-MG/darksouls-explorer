
package com.bernardomg.darksouls.explorer.persistence.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class DisabledPagination implements Pagination {

    @NonNull
    private final Integer page = -1;

    @NonNull
    private final Integer size = -1;

    @Override
    public final Boolean isPaged() {
        return false;
    }

}
