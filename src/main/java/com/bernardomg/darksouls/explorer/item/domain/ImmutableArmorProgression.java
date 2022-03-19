
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableArmorProgression implements ArmorProgression {

    @NonNull
    private final Iterable<ArmorLevel> levels;

    @NonNull
    private final String               armor;

    public ImmutableArmorProgression(@NonNull final String armor,
            @NonNull final Iterable<ArmorLevel> levels) {
        super();
        this.armor = armor;
        this.levels = levels;
    }

}
