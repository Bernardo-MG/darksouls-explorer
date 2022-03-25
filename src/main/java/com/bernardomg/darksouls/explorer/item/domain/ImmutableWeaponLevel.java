
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeaponLevel implements WeaponLevel {

    @NonNull
    private final String  dexterityBonus;

    @NonNull
    private final String  faithBonus;

    @NonNull
    private final Integer fireDamage;

    @NonNull
    private final Integer fireReduction;

    @NonNull
    private final String  intelligenceBonus;

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer lightningDamage;

    @NonNull
    private final Integer lightningReduction;

    @NonNull
    private final Integer magicDamage;

    @NonNull
    private final Integer magicReduction;

    @NonNull
    private final Integer pathLevel;

    @NonNull
    private final Integer physicalDamage;

    @NonNull
    private final Integer physicalReduction;

    @NonNull
    private final String  strengthBonus;

    public ImmutableWeaponLevel(@NonNull final Integer fireDamage,
            @NonNull final Integer level,
            @NonNull final Integer lightningDamage,
            @NonNull final Integer magicDamage,
            @NonNull final Integer pathLevel,
            @NonNull final Integer physicalDamage,
            @NonNull final String strengthBonus,
            @NonNull final String dexterityBonus,
            @NonNull final String intelligenceBonus,
            @NonNull final String faithBonus,
            @NonNull final Integer physicalReduction,
            @NonNull final Integer magicReduction,
            @NonNull final Integer fireReduction,
            @NonNull final Integer lightningReduction) {
        super();

        this.fireDamage = fireDamage;
        this.level = level;
        this.lightningDamage = lightningDamage;
        this.magicDamage = magicDamage;
        this.pathLevel = pathLevel;
        this.physicalDamage = physicalDamage;
        this.strengthBonus = strengthBonus;
        this.dexterityBonus = dexterityBonus;
        this.intelligenceBonus = intelligenceBonus;
        this.faithBonus = faithBonus;
        this.physicalReduction = physicalReduction;
        this.magicReduction = magicReduction;
        this.fireReduction = fireReduction;
        this.lightningReduction = lightningReduction;
    }

}
