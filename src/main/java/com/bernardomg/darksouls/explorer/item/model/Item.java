
package com.bernardomg.darksouls.explorer.item.model;

public interface Item {

    public Iterable<String> getDescription();

    public String getName();

    public void setDescription(final Iterable<String> description);

    public void setName(final String name);

}
