
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultItem implements Item {

    @NonNull
    private Iterable<String> description;

    @NonNull
    private Long             id;

    @NonNull
    private String           name;

    @NonNull
    private Iterable<String> tags;

    public DefaultItem(@NonNull final Long id, @NonNull final String name,
            @NonNull final Iterable<String> description,
            @NonNull final Iterable<String> tags) {
        super();

        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

}
