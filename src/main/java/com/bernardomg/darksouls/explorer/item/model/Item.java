
package com.bernardomg.darksouls.explorer.item.model;

public interface Item {

    public Long getId();

    public void setId(final Long id);

    public Iterable<String> getDescription();

    public String getName();

    public void setDescription(final Iterable<String> description);

    public void setName(final String name);

}
