
package com.bernardomg.darksouls.explorer.item.model;

public interface Item {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public void setDescription(final Iterable<String> description);

    public void setId(final Long id);

    public void setName(final String name);

}
