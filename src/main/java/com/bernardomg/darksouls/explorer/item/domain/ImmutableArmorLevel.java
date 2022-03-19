
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableArmorLevel implements ArmorLevel {

    @NonNull
    private final Integer bleedProtection;

    @NonNull
    private final Integer curseProtection;

    @NonNull
    private final Integer fireProtection;

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer lightningProtection;

    @NonNull
    private final Integer magicProtection;

    @NonNull
    private final Integer poisonProtection;

    @NonNull
    private final Integer regularProtection;

    @NonNull
    private final Integer slashProtection;

    @NonNull
    private final Integer strikeProtection;

    @NonNull
    private final Integer thrustProtection;

    public ImmutableArmorLevel(@NonNull final Integer level,
            @NonNull final Integer regularProtection,
            @NonNull final Integer strikeProtection,
            @NonNull final Integer slashProtection,
            @NonNull final Integer thrustProtection,
            @NonNull final Integer magicProtection,
            @NonNull final Integer fireProtection,
            @NonNull final Integer lightningProtection,
            @NonNull final Integer bleedProtection,
            @NonNull final Integer poisonProtection,
            @NonNull final Integer curseProtection) {
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
