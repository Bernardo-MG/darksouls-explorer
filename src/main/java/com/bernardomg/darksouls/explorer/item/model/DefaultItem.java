
package com.bernardomg.darksouls.explorer.item.model;

public final class DefaultItem implements Item {

    private String description;

    private String name;

    public DefaultItem() {
        super();
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public final void setName(final String name) {
        this.name = name;
    }

}
