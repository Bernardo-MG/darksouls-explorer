
package com.bernardomg.darksouls.explorer.test.domain;

public interface Item {

    public Iterable<String> getDescription();

    public Long getId();

    public String getName();

    public Iterable<String> getTags();

}
