
package com.bernardomg.darksouls.explorer.item.model;

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

    public DefaultItemSource(@NonNull final String item,
            @NonNull final String source, @NonNull final String relationship) {
        super();

        this.item = item;
        this.source = source;
        this.relationship = relationship;
    }

}
