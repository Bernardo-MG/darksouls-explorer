
package com.bernardomg.darksouls.explorer.graph.model;

public interface Item extends Node {

    public Iterable<String> getDescription();

    public void setDescription(final Iterable<String> value);

}
