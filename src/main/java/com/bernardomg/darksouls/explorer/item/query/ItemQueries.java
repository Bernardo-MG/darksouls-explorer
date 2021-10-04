
package com.bernardomg.darksouls.explorer.item.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;

public interface ItemQueries {

    public Page<Item> findAll(final Pageable page);

    public Iterable<ItemSource> findAllSources();

}
