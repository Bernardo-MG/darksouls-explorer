
package com.bernardomg.darksouls.explorer.item.service;

import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;

public interface ItemService {

    public Iterable<Item> getAll(final Pageable page);

    public Iterable<ItemSource> getAllSources();

}
