
package com.bernardomg.darksouls.explorer.item.shield.domain;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoShield implements Shield {

    @NonNull
    private WeaponBonus           bonus           = new DtoWeaponBonus();

    @NonNull
    private WeaponDamage          damage          = new DtoWeaponDamage();

    @NonNull
    private WeaponDamageReduction damageReduction = new DtoWeaponDamageReduction();

    @NonNull
    private String                description     = "";

    @NonNull
    private Integer               durability      = 0;

    @NonNull
    private Long                  id              = -1l;

    @NonNull
    private String                name            = "";

    @NonNull
    private WeaponRequirements    requirements    = new DtoWeaponRequirements();

    @NonNull
    private Long                  weight          = 0l;

}
