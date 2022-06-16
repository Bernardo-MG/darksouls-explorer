
package com.bernardomg.darksouls.explorer.item.shield.domain;

import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;

public interface Shield {

    public WeaponBonus getBonus();

    public WeaponDamage getDamage();

    public WeaponDamageReduction getDamageReduction();

    public String getDescription();

    public Integer getDurability();

    public Long getId();

    public String getName();

    public String getType();

    public WeaponRequirements getRequirements();

    public Long getWeight();

}
