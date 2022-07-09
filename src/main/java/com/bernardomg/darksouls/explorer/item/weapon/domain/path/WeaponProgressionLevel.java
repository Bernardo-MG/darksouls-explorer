
package com.bernardomg.darksouls.explorer.item.weapon.domain.path;

import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponDamageReduction;

public interface WeaponProgressionLevel {

    public WeaponBonus getBonus();

    public WeaponDamage getDamage();

    public WeaponDamageReduction getDamageReduction();

    public Integer getLevel();

    public Integer getPathLevel();

    public Integer getStability();

}
