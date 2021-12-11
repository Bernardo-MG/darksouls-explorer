
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableItem implements Item {

    @NonNull
    private final Iterable<String> description;

    @NonNull
    private final Long             id;

    @NonNull
    private final String           name;

    @NonNull
    private final Iterable<String> tags;

    public ImmutableItem(@NonNull final Long id, @NonNull final String name,
            @NonNull final Iterable<String> description,
            @NonNull final Iterable<String> tags) {
        super();

        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

}
