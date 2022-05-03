
package com.bernardomg.darksouls.explorer.item.spell.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

public interface Spell {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public ItemStats getStats();

}
