
package com.bernardomg.darksouls.explorer.item.talisman.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

public interface Talisman {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public ItemStats getStats();

}
