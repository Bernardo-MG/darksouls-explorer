
package com.bernardomg.darksouls.explorer.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;

public interface ItemService {

    public Page<Item> getAll(final Pageable page);

    public Page<ItemSource> getSources(final Long id, final Pageable page);

}
