
package com.bernardomg.darksouls.explorer.item.itemdata.domain;

public interface Item {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public ItemRequirements getRequirements();

    public ItemStats getStats();

    public Iterable<String> getTags();

}
