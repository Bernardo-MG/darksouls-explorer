
package com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class DtoWeaponAdjustment implements WeaponAdjustment {

    private Integer adjustment   = 0;

    private Integer faith        = 0;

    private Long    id           = -1l;

    private Integer intelligence = 0;

    private String  name         = "";

}
