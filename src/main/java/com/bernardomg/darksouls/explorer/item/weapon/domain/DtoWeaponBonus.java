
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponBonus implements WeaponBonus {

    @NonNull
    private String dexterity    = "";

    @NonNull
    private String faith        = "";

    @NonNull
    private String intelligence = "";

    @NonNull
    private String strength     = "";

}
