
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponProgressionPath
        implements WeaponProgressionPath {

    @NonNull
    private String                path;

    @NonNull
    private Iterable<WeaponLevel> levels;

    public ImmutableWeaponProgressionPath(@NonNull final String path,
            @NonNull final Iterable<WeaponLevel> levels) {
        super();

        this.path = path;
        this.levels = levels;
    }

}
