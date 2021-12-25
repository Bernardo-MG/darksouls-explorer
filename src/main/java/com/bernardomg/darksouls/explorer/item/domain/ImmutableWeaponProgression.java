
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponProgression implements WeaponProgression {

    @NonNull
    private String                weapon;

    @NonNull
    private String                path;

    @NonNull
    private Iterable<WeaponLevel> levels;

    public ImmutableWeaponProgression(@NonNull final String weapon,
            @NonNull final String path,
            @NonNull final Iterable<WeaponLevel> levels) {
        super();

        this.weapon = weapon;
        this.path = path;
        this.levels = levels;
    }

}
