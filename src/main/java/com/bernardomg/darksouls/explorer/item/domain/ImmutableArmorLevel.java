
package com.bernardomg.darksouls.explorer.item.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableArmorLevel implements ArmorLevel {

    @NonNull
    private final Float   bleedProtection;

    @NonNull
    private final Float   curseProtection;

    @NonNull
    private final Float   fireProtection;

    @NonNull
    private final Integer level;

    @NonNull
    private final Float   lightningProtection;

    @NonNull
    private final Float   magicProtection;

    @NonNull
    private final Float   poisonProtection;

    @NonNull
    private final Float   regularProtection;

    @NonNull
    private final Float   slashProtection;

    @NonNull
    private final Float   strikeProtection;

    @NonNull
    private final Float   thrustProtection;

    public ImmutableArmorLevel(@NonNull final Integer level,
            @NonNull final Float regularProtection,
            @NonNull final Float strikeProtection,
            @NonNull final Float slashProtection,
            @NonNull final Float thrustProtection,
            @NonNull final Float magicProtection,
            @NonNull final Float fireProtection,
            @NonNull final Float lightningProtection,
            @NonNull final Float bleedProtection,
            @NonNull final Float poisonProtection,
            @NonNull final Float curseProtection) {
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
