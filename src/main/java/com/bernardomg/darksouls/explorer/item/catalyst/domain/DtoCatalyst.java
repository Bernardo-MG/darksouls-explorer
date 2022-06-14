
package com.bernardomg.darksouls.explorer.item.catalyst.domain;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DtoCatalyst implements Catalyst {

    @NonNull
    private String             description;

    @NonNull
    private String             dexterityBonus;

    @NonNull
    private Integer            durability   = 0;

    @NonNull
    private String             faithBonus;

    @NonNull
    private Integer            fireDamage;

    @NonNull
    private Float              fireReduction;

    @NonNull
    private Long               id;

    @NonNull
    private String             intelligenceBonus;

    @NonNull
    private Integer            lightningDamage;

    @NonNull
    private Float              lightningReduction;

    @NonNull
    private Integer            magicDamage;

    @NonNull
    private Float              magicReduction;

    @NonNull
    private String             name;

    @NonNull
    private Integer            physicalDamage;

    @NonNull
    private Float              physicalReduction;

    @NonNull
    private WeaponRequirements requirements = new DtoWeaponRequirements();

    @NonNull
    private String             strengthBonus;

    @NonNull
    private Long               weight       = 0l;

}
