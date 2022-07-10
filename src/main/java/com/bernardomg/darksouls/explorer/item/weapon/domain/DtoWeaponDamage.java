
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponDamage implements WeaponDamage {

    @NonNull
    private Integer critical  = 0;

    @NonNull
    private Integer fire      = 0;

    @NonNull
    private Integer lightning = 0;

    @NonNull
    private Integer magic     = 0;

    @NonNull
    private Integer physical  = 0;

}
