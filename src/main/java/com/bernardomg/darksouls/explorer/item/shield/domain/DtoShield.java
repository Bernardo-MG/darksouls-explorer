
package com.bernardomg.darksouls.explorer.item.shield.domain;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoShield implements Shield {

    @NonNull
    private String             description  = "";

    @NonNull
    private Long               durability   = 0l;

    @NonNull
    private Long               id           = -1l;

    @NonNull
    private String             name         = "";

    @NonNull
    private WeaponRequirements requirements = new DtoWeaponRequirements();

    @NonNull
    private Long               weight       = 0l;

}
