
package com.bernardomg.darksouls.explorer.item.talisman.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableTalisman implements Talisman {

    @NonNull
    private final String  description;

    @NonNull
    private final String  dexterityBonus;

    @NonNull
    private Integer       durability = 0;

    @NonNull
    private final String  faithBonus;

    @NonNull
    private final Integer fireDamage;

    @NonNull
    private final Float   fireReduction;

    @NonNull
    private final Long    id;

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
    private final String  name;

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

    @NonNull
    private Long          weight     = 0l;

}
