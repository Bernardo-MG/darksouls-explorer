
package com.bernardomg.darksouls.explorer.model;

import java.util.Collections;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public final class DefaultGraph implements Graph {

    private Iterable<Link>   links = Collections.emptyList();

    private Iterable<Node>   nodes = Collections.emptyList();

    private Iterable<String> types = Collections.emptyList();

    public DefaultGraph() {
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

        final DefaultGraph other = (DefaultGraph) obj;
        return Objects.equals(nodes, other.nodes)
                && Objects.equals(links, other.links)
                && Objects.equals(types, other.types);
    }

    @Override
    public Iterable<Link> getLinks() {
        return links;
    }

    @Override
    public Iterable<Node> getNodes() {
        return nodes;
    }

    @Override
    public Iterable<String> getTypes() {
        return types;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(nodes, links, types);
    }

    @Override
    public void setLinks(final Iterable<Link> links) {
        this.links = links;
    }

    @Override
    public void setNodes(final Iterable<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void setTypes(final Iterable<String> types) {
        this.types = types;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("nodes", nodes)
                .add("links", links).add("types", types).toString();
    }

}
