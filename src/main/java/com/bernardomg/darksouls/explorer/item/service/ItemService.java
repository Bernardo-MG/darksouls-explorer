
package com.bernardomg.darksouls.explorer.item.service;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;

public interface ItemService {

    public Iterable<Item> getAll();

    public Iterable<ItemSource> getAllSources();

}
