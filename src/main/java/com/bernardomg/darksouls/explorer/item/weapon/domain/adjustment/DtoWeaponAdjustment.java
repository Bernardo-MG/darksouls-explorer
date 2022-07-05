
package com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class DtoWeaponAdjustment implements WeaponAdjustment {

    private Long    id;

    private String  name;

    private Integer faith        = 0;

    private Integer intelligence = 0;

    private Integer adjustment   = 0;

}
