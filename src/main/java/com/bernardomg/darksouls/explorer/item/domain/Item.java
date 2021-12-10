
package com.bernardomg.darksouls.explorer.item.domain;

public interface Item {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public Iterable<String> getTags();

    public void setDescription(final Iterable<String> description);

    public void setId(final Long id);

    public void setName(final String name);

    public void setTags(final Iterable<String> tags);

}
