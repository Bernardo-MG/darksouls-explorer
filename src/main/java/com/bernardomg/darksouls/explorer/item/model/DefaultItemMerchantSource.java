
package com.bernardomg.darksouls.explorer.item.model;

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

    public DefaultItemMerchantSource(@NonNull final String item,
            @NonNull final String source, @NonNull final String relationship,
            @NonNull final Double cost) {
        super();

        this.item = item;
        this.source = source;
        this.relationship = relationship;
        this.cost = cost;
    }

}
