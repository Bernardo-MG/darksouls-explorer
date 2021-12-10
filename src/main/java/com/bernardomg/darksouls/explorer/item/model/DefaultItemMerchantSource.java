
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
    private Float  cost;

    public DefaultItemMerchantSource(@NonNull final String item,
            @NonNull final String source, @NonNull final String relationship,
            @NonNull final Float cost) {
        super();

        this.item = item;
        this.source = source;
        this.relationship = relationship;
        this.cost = cost;
    }

}
