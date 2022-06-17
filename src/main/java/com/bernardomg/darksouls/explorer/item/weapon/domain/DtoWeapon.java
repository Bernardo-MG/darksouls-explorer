
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoWeapon implements Weapon {

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
    private String                subtype         = "";

    @NonNull
    private String                type            = "";

    @NonNull
    private Long                  weight          = 0l;

}
