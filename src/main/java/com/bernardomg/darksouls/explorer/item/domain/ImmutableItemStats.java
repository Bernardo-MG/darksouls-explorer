
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;

@Data
public final class ImmutableItemStats implements ItemStats {

    private final Iterable<String> attacks;

    private final Integer          durability;

    private final String           type;

    private final Long             weight;

    public ImmutableItemStats(final String type, final Long weight,
            final Integer durability, final Iterable<String> attacks) {
        super();

        this.type = type;
        this.weight = weight;
        this.durability = durability;
        this.attacks = attacks;
    }

}
