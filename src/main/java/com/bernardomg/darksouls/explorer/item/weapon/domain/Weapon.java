
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

public interface Weapon {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public WeaponRequirements getRequirements();

    public ItemStats getStats();

}
