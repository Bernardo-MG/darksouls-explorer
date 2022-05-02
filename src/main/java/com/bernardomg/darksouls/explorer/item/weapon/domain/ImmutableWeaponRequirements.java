
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import lombok.Data;

@Data
public final class ImmutableWeaponRequirements implements WeaponRequirements {

    private final Integer dexterity;

    private final Integer faith;

    private final Integer intelligence;

    private final Integer strength;

}
