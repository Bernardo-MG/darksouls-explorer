
package com.bernardomg.darksouls.explorer.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;
import com.bernardomg.darksouls.explorer.item.query.ItemQueries;

@Service
public final class DefaultItemService implements ItemService {

    private final ItemQueries queries;

    @Autowired
    public DefaultItemService(final ItemQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Iterable<Item> getAll(final Pageable page) {
        return queries.findAll(page);
    }

    @Override
    public final Iterable<ItemSource> getAllSources(final Pageable page) {
        return queries.findAllSources(page);
    }

}
