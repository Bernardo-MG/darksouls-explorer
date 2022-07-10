
package com.bernardomg.darksouls.explorer.item.armor.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoArmorLevel implements ArmorLevel {

    @NonNull
    private Float   bleedProtection;

    @NonNull
    private Float   curseProtection;

    @NonNull
    private Float   fireProtection;

    @NonNull
    private Integer level;

    @NonNull
    private Float   lightningProtection;

    @NonNull
    private Float   magicProtection;

    @NonNull
    private Float   poisonProtection;

    @NonNull
    private Float   regularProtection;

    @NonNull
    private Float   slashProtection;

    @NonNull
    private Float   strikeProtection;

    @NonNull
    private Float   thrustProtection;

}
