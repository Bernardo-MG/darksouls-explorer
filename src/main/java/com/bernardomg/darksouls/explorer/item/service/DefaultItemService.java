
package com.bernardomg.darksouls.explorer.item.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    public final Iterable<Item> getAll() {
        return queries.findAll();
    }

    @Override
    public final Iterable<ItemSource> getAllSources() {
        return queries.findAllSources();
    }

}
