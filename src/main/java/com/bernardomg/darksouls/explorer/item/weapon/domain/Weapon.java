
package com.bernardomg.darksouls.explorer.item.weapon.domain;

public interface Weapon {

    public String getDescription();

    public Integer getDurability();

    public Long getId();

    public String getName();

    public WeaponRequirements getRequirements();

    public Long getWeight();

}
