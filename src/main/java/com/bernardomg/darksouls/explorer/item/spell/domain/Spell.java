
package com.bernardomg.darksouls.explorer.item.spell.domain;

public interface Spell {

    public String getDescription();

    public Long getId();

    public String getName();

    public SpellRequirements getRequirements();

    public String getSchool();

    public Integer getSlots();

    public Integer getUses();

}
