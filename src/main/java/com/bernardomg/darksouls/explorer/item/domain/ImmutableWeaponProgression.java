
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponProgression implements WeaponProgression {

    @NonNull
    private final Iterable<WeaponProgressionPath> paths;

    @NonNull
    private final String                          weapon;

    public ImmutableWeaponProgression(@NonNull final String weapon,
            @NonNull final Iterable<WeaponProgressionPath> paths) {
        super();

        this.weapon = weapon;
        this.paths = paths;
    }

}
