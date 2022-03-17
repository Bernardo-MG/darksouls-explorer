
package com.bernardomg.darksouls.explorer.item.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.query.AllItemsQuery;
import com.bernardomg.darksouls.explorer.item.query.ItemSourcesQuery;
import com.bernardomg.darksouls.explorer.item.query.WeaponProgressionQuery;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;
import com.bernardomg.darksouls.explorer.persistence.DslQuery;
import com.bernardomg.darksouls.explorer.persistence.QueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.StringQuery;

@Service
public final class DefaultItemService implements ItemService {

    private final QueryCommandExecutor queryExecutor;

    @Autowired
    public DefaultItemService(final QueryCommandExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Page<Item> getAll(final ItemRequest request,
            final Pageable page) {
        final DslQuery<Map<String, Object>, Item> query;

        query = new AllItemsQuery(request.getName(), request.getTags());

        return queryExecutor.fetch(query, page);
    }

    @Override
    public final Page<ItemSource> getSources(final Long id,
            final Pageable page) {
        final Map<String, Object> params;
        final StringQuery<Map<String, Object>, ItemSource> query;

        query = new ItemSourcesQuery();

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetch(query, params, page);
    }

    @Override
    public final WeaponProgression getWeaponLevels(final Long id) {
        final Map<String, Object> params;
        final StringQuery<Collection<Map<String, Object>>, WeaponProgression> query;

        params = new HashMap<>();
        params.put("id", id);

        query = new WeaponProgressionQuery();

        return queryExecutor.fetchOne(query, params);
    }

}
