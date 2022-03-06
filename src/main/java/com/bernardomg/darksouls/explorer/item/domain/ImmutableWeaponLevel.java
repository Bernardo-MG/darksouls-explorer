
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponLevel implements WeaponLevel {

    @NonNull
    private final Integer fireDamage;

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer pathLevel;

    @NonNull
    private final Integer lightningDamage;

    @NonNull
    private final Integer magicDamage;

    @NonNull
    private final Integer physicalDamage;

    public ImmutableWeaponLevel(@NonNull final Integer level,
            @NonNull final Integer pathLevel,
            @NonNull final Integer physicalDamage,
            @NonNull final Integer magicDamage,
            @NonNull final Integer fireDamage,
            @NonNull final Integer lightningDamage) {
        super();

        this.level = level;
        this.pathLevel = pathLevel;
        this.physicalDamage = physicalDamage;
        this.magicDamage = magicDamage;
        this.fireDamage = fireDamage;
        this.lightningDamage = lightningDamage;
    }

}
