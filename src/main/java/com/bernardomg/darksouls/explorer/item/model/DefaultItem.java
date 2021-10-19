
package com.bernardomg.darksouls.explorer.item.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultItem implements Item {

    @NonNull
    private String description;

    @NonNull
    private String name;

    public DefaultItem(@NonNull final String name,
            @NonNull final String description) {
        super();

        this.name = name;
        this.description = description;
    }

}
