
package com.bernardomg.darksouls.explorer.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.query.ItemQueries;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;

@Service
public final class DefaultItemService implements ItemService {

    private final ItemQueries queries;

    @Autowired
    public DefaultItemService(final ItemQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Page<Item> getAll(final ItemRequest request,
            final Pageable page) {
        return queries.findAll(request.getName(), request.getTags(), page);
    }

    @Override
    public final Page<ItemSource> getSources(final Long id,
            final Pageable page) {
        return queries.findSources(id, page);
    }

}
