
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class DefaultItemMerchantSource implements ItemMerchantSource {

    @NonNull
    private String item;

    @NonNull
    private String relationship;

    @NonNull
    private String source;

    @NonNull
    private Double cost;

    @NonNull
    private String location;

    public DefaultItemMerchantSource(@NonNull final String item,
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
