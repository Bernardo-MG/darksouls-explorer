
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.Objects;

import com.google.common.base.MoreObjects;

public final class DefaultItem implements Item {

    private Iterable<String> description;

    private Long             id;

    private String           name;

    public DefaultItem() {
        super();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        final DefaultItem other = (DefaultItem) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public final Iterable<String> getDescription() {
        return description;
    }

    @Override
    public final Long getId() {
        return id;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public final void setDescription(final Iterable<String> description) {
        this.description = description;
    }

    @Override
    public final void setId(final Long value) {
        id = value;
    }

    @Override
    public final void setName(final String value) {
        name = value;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name)
                .add("description", description).toString();
    }

}
