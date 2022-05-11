
package com.bernardomg.darksouls.explorer.item.catalyst.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

public interface Catalyst {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public ItemStats getStats();

}
