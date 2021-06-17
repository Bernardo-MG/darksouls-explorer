
package com.bernardomg.darksouls.explorer.model;

import java.util.Objects;

import com.google.common.base.MoreObjects;

public final class DefaultNode implements Node {

    private Long   id;

    private String name;

    public DefaultNode() {
        super();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final DefaultNode other = (DefaultNode) obj;
        return Objects.equals(id, other.id);
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
    public final void setId(final Long value) {
        id = value;
    }

    @Override
    public final void setName(final String value) {
        name = value;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).add("id", id)
                .toString();
    }

}
