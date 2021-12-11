
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutableItemSource implements ItemSource {

    @NonNull
    private final String item;

    @NonNull
    private final String location;

    @NonNull
    private final String relationship;

    @NonNull
    private final String source;

    public ImmutableItemSource(@NonNull final String item,
            @NonNull final String source, @NonNull final String relationship,
            @NonNull final String location) {
        super();

        this.item = item;
        this.source = source;
        this.relationship = relationship;
        this.location = location;
    }

}
