
package com.bernardomg.darksouls.explorer.graph.model;

public interface Graph {

    public Iterable<Link> getLinks();

    public Iterable<Node> getNodes();

    public Iterable<String> getTypes();

    public void setLinks(final Iterable<Link> links);

    public void setNodes(final Iterable<Node> nodes);

    public void setTypes(final Iterable<String> types);

}
