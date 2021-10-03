
package com.bernardomg.darksouls.explorer.item.query;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;

public interface ItemQueries {

    public Iterable<Item> findAll();

    public Iterable<ItemSource> findAllSources();

}
