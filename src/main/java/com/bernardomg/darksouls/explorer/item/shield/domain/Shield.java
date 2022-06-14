
package com.bernardomg.darksouls.explorer.item.shield.domain;

import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;

public interface Shield {

    public String getDescription();

    public Long getDurability();

    public Long getId();

    public String getName();

    public WeaponRequirements getRequirements();

    public Long getWeight();

}
