
package com.bernardomg.darksouls.explorer.item.spell.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableSpell implements Spell {

    @NonNull
    private final Iterable<String> description;

    @NonNull
    private final Long             id;

    @NonNull
    private final String           name;

    @NonNull
    private final ItemStats        stats;

    public ImmutableSpell(@NonNull final Long id, @NonNull final String name,
            @NonNull final ItemStats stats,
            @NonNull final Iterable<String> description) {
        super();

        this.id = id;
        this.name = name;
        this.stats = stats;
        this.description = description;
    }

}
