
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponDamageReduction implements WeaponDamageReduction {

    @NonNull
    private Float fire      = 0f;

    @NonNull
    private Float lightning = 0f;

    @NonNull
    private Float magic     = 0f;

    @NonNull
    private Float physical  = 0f;

}
