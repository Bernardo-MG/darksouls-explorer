
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamageReduction;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeaponProgressionLevel implements WeaponProgressionLevel {

    @NonNull
    private WeaponBonus           bonus           = new DtoWeaponBonus();

    @NonNull
    private WeaponDamage          damage          = new DtoWeaponDamage();

    @NonNull
    private WeaponDamageReduction damageReduction = new DtoWeaponDamageReduction();

    @NonNull
    private Integer               level           = 0;

    @NonNull
    private String                name            = "";

    @NonNull
    private Integer               pathLevel       = 0;

    @NonNull
    private String                path            = "";

    @NonNull
    private Integer               stability       = 0;

}
