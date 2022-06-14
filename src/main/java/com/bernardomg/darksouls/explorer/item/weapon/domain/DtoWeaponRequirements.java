
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponRequirements implements WeaponRequirements {

    @NonNull
    private Integer dexterity    = 0;

    @NonNull
    private Integer faith        = 0;

    @NonNull
    private Integer intelligence = 0;

    @NonNull
    private Integer strength     = 0;

}
