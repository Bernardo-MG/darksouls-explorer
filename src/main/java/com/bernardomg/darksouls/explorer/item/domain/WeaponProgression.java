
package com.bernardomg.darksouls.explorer.item.domain;

public interface WeaponProgression {

    public String getWeapon();

    public String getPath();

    public Iterable<WeaponLevel> getLevels();

}
