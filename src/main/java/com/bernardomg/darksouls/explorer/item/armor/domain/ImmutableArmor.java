
package com.bernardomg.darksouls.explorer.item.armor.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableArmor implements Armor {

    @NonNull
    private final Iterable<String> description;

    @NonNull
    private final Long             id;

    @NonNull
    private final String           name;

    @NonNull
    private final ItemStats        stats;

    public ImmutableArmor(@NonNull final Long id, @NonNull final String name,
            @NonNull final ItemStats stats,
            @NonNull final Iterable<String> description) {
        super();

        this.id = id;
        this.name = name;
        this.stats = stats;
        this.description = description;
    }

}
