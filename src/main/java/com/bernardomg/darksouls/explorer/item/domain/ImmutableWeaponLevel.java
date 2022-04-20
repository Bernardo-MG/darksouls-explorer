
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
    private final Float   fireReduction;

    @NonNull
    private final String  intelligenceBonus;

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer lightningDamage;

    @NonNull
    private final Float   lightningReduction;

    @NonNull
    private final Integer magicDamage;

    @NonNull
    private final Float   magicReduction;

    @NonNull
    private final String  path;

    @NonNull
    private final Integer pathLevel;

    @NonNull
    private final Integer physicalDamage;

    @NonNull
    private final Float   physicalReduction;

    @NonNull
    private final String  strengthBonus;

    @NonNull
    private final String  weapon;

    public ImmutableWeaponLevel(@NonNull final String weapon,
            @NonNull final String path, @NonNull final Integer level,
            @NonNull final Integer pathLevel,
            @NonNull final Integer physicalDamage,
            @NonNull final Integer magicDamage,
            @NonNull final Integer fireDamage,
            @NonNull final Integer lightningDamage,
            @NonNull final String strengthBonus,
            @NonNull final String dexterityBonus,
            @NonNull final String intelligenceBonus,
            @NonNull final String faithBonus,
            @NonNull final Float physicalReduction,
            @NonNull final Float magicReduction,
            @NonNull final Float fireReduction,
            @NonNull final Float lightningReduction) {
        super();

        this.weapon = weapon;
        this.path = path;
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
