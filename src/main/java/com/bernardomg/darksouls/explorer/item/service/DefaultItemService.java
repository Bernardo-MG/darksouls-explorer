
package com.bernardomg.darksouls.explorer.item.service;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemRequest;
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
    public final Page<Item> getAll(final ItemRequest request,
            final Pageable page) {
        final Page<Item> items;

        if (IterableUtils.isEmpty(request.getTags())) {
            items = queries.findAll(page);
        } else {
            items = queries.findAllByTags(request.getTags(), page);
        }

        return items;
    }

    @Override
    public final Page<ItemSource> getSources(final Long id,
            final Pageable page) {
        return queries.findSources(id, page);
    }

}
