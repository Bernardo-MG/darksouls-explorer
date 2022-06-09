
package com.bernardomg.darksouls.explorer.item.armor.domain;

public interface ArmorProgression {

    public String getArmor();

    public Iterable<? extends ArmorLevel> getLevels();

}
