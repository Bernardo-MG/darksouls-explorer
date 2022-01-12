
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponLevel implements WeaponLevel {

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer physicalDamage;

    public ImmutableWeaponLevel(@NonNull final Integer level,
            @NonNull final Integer physicalDamage) {
        super();

        this.level = level;
        this.physicalDamage = physicalDamage;
    }

}
