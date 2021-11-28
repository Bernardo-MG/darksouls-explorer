
package com.bernardomg.darksouls.explorer.item.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultItem implements Item {

    @NonNull
    private Long             id;

    @NonNull
    private Iterable<String> description;

    @NonNull
    private String           name;

    public DefaultItem(@NonNull final Long id, @NonNull final String name,
            @NonNull final Iterable<String> description) {
        super();

        this.id = id;
        this.name = name;
        this.description = description;
    }

}
