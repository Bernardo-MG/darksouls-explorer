
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableArmorLevel implements ArmorLevel {

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer regularProtection;

    @NonNull
    private final Integer strikeProtection;

    @NonNull
    private final Integer slashProtection;

    @NonNull
    private final Integer thrustProtection;

    @NonNull
    private final Integer magicProtection;

    @NonNull
    private final Integer fireProtection;

    @NonNull
    private final Integer lightningProtection;

    @NonNull
    private final Integer bleedProtection;

    @NonNull
    private final Integer poisonProtection;

    @NonNull
    private final Integer curseProtection;

    public ImmutableArmorLevel(@NonNull Integer level,
            @NonNull Integer regularProtection,
            @NonNull Integer strikeProtection, @NonNull Integer slashProtection,
            @NonNull Integer thrustProtection, @NonNull Integer magicProtection,
            @NonNull Integer fireProtection,
            @NonNull Integer lightningProtection,
            @NonNull Integer bleedProtection, @NonNull Integer poisonProtection,
            @NonNull Integer curseProtection) {
        super();

        this.level = level;
        this.regularProtection = regularProtection;
        this.strikeProtection = strikeProtection;
        this.slashProtection = slashProtection;
        this.thrustProtection = thrustProtection;
        this.magicProtection = magicProtection;
        this.fireProtection = fireProtection;
        this.lightningProtection = lightningProtection;
        this.bleedProtection = bleedProtection;
        this.poisonProtection = poisonProtection;
        this.curseProtection = curseProtection;
    }

}
