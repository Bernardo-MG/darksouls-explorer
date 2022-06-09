
package com.bernardomg.darksouls.explorer.item.armor.domain;

import java.util.Collections;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableArmorProgression implements ArmorProgression {

    @NonNull
    private final String                         armor;

    @NonNull
    private final Iterable<? extends ArmorLevel> levels;

    public ImmutableArmorProgression() {
        super();

        armor = "";
        levels = Collections.emptyList();
    }

    public ImmutableArmorProgression(@NonNull final String armor,
            @NonNull final Iterable<? extends ArmorLevel> levels) {
        super();

        this.armor = armor;
        this.levels = levels;
    }

}
