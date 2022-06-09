
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponProgressionLevel implements WeaponProgressionLevel {

    @NonNull
    private Integer critical;

    @NonNull
    private String  dexterityBonus;

    @NonNull
    private String  faithBonus;

    @NonNull
    private Integer fireDamage;

    @NonNull
    private Float   fireReduction;

    @NonNull
    private String  intelligenceBonus;

    @NonNull
    private Integer level;

    @NonNull
    private Integer lightningDamage;

    @NonNull
    private Float   lightningReduction;

    @NonNull
    private Integer magicDamage;

    @NonNull
    private Float   magicReduction;

    @NonNull
    private String  name;

    @NonNull
    private String  path;

    @NonNull
    private Integer pathLevel;

    @NonNull
    private Integer physicalDamage;

    @NonNull
    private Float   physicalReduction;

    @NonNull
    private Integer stability;

    @NonNull
    private String  strengthBonus;

}
