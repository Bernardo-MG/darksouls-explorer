
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.model.Item;

public interface ItemQueries {

    public Iterable<Item> findAll();

    public Optional<Item> findOneByName(final String name);

}
