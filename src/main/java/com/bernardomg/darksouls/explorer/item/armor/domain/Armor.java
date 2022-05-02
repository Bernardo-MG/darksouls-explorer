
package com.bernardomg.darksouls.explorer.item.armor.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

public interface Armor {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public ItemStats getStats();

}
