
package com.bernardomg.darksouls.explorer.item.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;

public interface ItemQueries {

    public Page<Item> findAll(final String name, final Iterable<String> tags,
            final Pageable page);

    public Page<ItemSource> findSources(final Long id, final Pageable page);

}
