
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import java.util.Collections;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponProgression implements WeaponProgression {

    @NonNull
    private final Iterable<WeaponProgressionPath> paths;

    @NonNull
    private final String                          weapon;

    public ImmutableWeaponProgression() {
        super();

        weapon = "";
        paths = Collections.emptyList();
    }

    public ImmutableWeaponProgression(@NonNull final String weapon,
            @NonNull final Iterable<WeaponProgressionPath> paths) {
        super();

        this.weapon = weapon;
        this.paths = paths;
    }

}
