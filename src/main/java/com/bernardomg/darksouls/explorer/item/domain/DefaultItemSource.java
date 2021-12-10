
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class DefaultItemSource implements ItemSource {

    @NonNull
    private String item;

    @NonNull
    private String relationship;

    @NonNull
    private String source;

    @NonNull
    private String location;

    public DefaultItemSource(@NonNull final String item,
            @NonNull final String source, @NonNull final String relationship,
            @NonNull final String location) {
        super();

        this.item = item;
        this.source = source;
        this.relationship = relationship;
        this.location = location;
    }

}
