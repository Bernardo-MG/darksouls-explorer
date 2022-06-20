
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponProgressionPath
        implements WeaponProgressionPath {

    @NonNull
    private Iterable<WeaponProgressionLevel> levels;

    @NonNull
    private String                           path;

    public ImmutableWeaponProgressionPath(@NonNull final String path,
            @NonNull final Iterable<WeaponProgressionLevel> levels) {
        super();

        this.path = path;
        this.levels = levels;
    }

}
