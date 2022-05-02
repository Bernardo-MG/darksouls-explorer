
package com.bernardomg.darksouls.explorer.item.itemdata.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutableItemSource implements ItemSource {

    @NonNull
    private final String item;

    @NonNull
    private final Long   itemId;

    @NonNull
    private final String location;

    @NonNull
    private final Long   locationId;

    @NonNull
    private final String relationship;

    @NonNull
    private final String source;

    @NonNull
    private final Long   sourceId;

    public ImmutableItemSource(@NonNull final Long itemId,
            @NonNull final String item, @NonNull final Long sourceId,
            @NonNull final String source, @NonNull final String relationship,
            @NonNull final Long locationId, @NonNull final String location) {
        super();

        this.itemId = itemId;
        this.item = item;
        this.sourceId = sourceId;
        this.source = source;
        this.relationship = relationship;
        this.locationId = locationId;
        this.location = location;
    }

}
