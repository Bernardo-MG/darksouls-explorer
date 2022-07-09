
package com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponAdjustmentLevel implements WeaponAdjustmentLevel {

    @NonNull
    private Integer adjustment   = 0;

    @NonNull
    private Integer faith        = 0;

    @NonNull
    private Long    id           = -1l;

    @NonNull
    private Integer intelligence = 0;

    @NonNull
    private String  name         = "";

}
