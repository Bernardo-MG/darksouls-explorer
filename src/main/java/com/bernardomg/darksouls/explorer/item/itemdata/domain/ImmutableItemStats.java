
package com.bernardomg.darksouls.explorer.item.itemdata.domain;

import lombok.Data;

@Data
public final class ImmutableItemStats implements ItemStats {

    private final Integer durability;

    private final Long    weight;

    public ImmutableItemStats(final Long weight, final Integer durability) {
        super();

        this.weight = weight;
        this.durability = durability;
    }

}
