
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutableItemMerchantSource implements ItemMerchantSource {

    @NonNull
    private final String item;

    @NonNull
    private final String relationship;

    @NonNull
    private final String source;

    @NonNull
    private final Double cost;

    @NonNull
    private final String location;

    public ImmutableItemMerchantSource(@NonNull final String item,
            @NonNull final String source, @NonNull final String relationship,
            @NonNull final String location, @NonNull final Double cost) {
        super();

        this.item = item;
        this.source = source;
        this.relationship = relationship;
        this.location = location;
        this.cost = cost;
    }

}
