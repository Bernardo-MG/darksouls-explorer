
package com.bernardomg.darksouls.explorer.item.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.query.AllItemsQuery;
import com.bernardomg.darksouls.explorer.item.query.ArmorProgressionQuery;
import com.bernardomg.darksouls.explorer.item.query.ItemQuery;
import com.bernardomg.darksouls.explorer.item.query.ItemSourcesQuery;
import com.bernardomg.darksouls.explorer.item.query.WeaponProgressionQuery;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

@Service
public final class DefaultItemService implements ItemService {

    private final QueryExecutor<String> queryExecutor;

    @Autowired
    public DefaultItemService(final QueryExecutor<String> queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Page<Item> getAll(final ItemRequest request,
            final Pageable page) {
        final Query<List<Item>> query;
        final Collection<String> tags;

        tags = request.getSelectors()
            .stream()
            .map(String::toLowerCase)
            .map(StringUtils::capitalize)
            .collect(Collectors.toList());

        query = new AllItemsQuery(request.getName(), tags);

        return queryExecutor.fetch(query.getStatement(), query::getOutput,
            page);
    }

    @Override
    public final ArmorProgression getArmorLevels(final Long id) {
        final Map<String, Object> params;
        final Query<List<ArmorProgression>> query;
        final Iterable<ArmorProgression> data;
        final ArmorProgression result;

        params = new HashMap<>();
        params.put("id", id);

        query = new ArmorProgressionQuery();

        data = queryExecutor.fetch(query.getStatement(), query::getOutput,
            params);

        if (data.iterator()
            .hasNext()) {
            result = data.iterator()
                .next();
        } else {
            result = new ImmutableArmorProgression("", Collections.emptyList());
        }

        return result;
    }

    @Override
    public final Item getOne(final Long id) {
        final Map<String, Object> params;
        final Query<Item> query;

        query = new ItemQuery();

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor
            .fetchOne(query.getStatement(), query::getOutput, params)
            .orElse(null);
    }

    @Override
    public final Page<ItemSource> getSources(final Long id,
            final Pageable page) {
        final Map<String, Object> params;
        final Query<List<ItemSource>> query;

        query = new ItemSourcesQuery();

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetch(query.getStatement(), query::getOutput,
            params, page);
    }

    @Override
    public final WeaponProgression getWeaponLevels(final Long id) {
        final Map<String, Object> params;
        final Query<List<WeaponProgression>> query;
        final Iterable<WeaponProgression> data;
        final WeaponProgression result;

        params = new HashMap<>();
        params.put("id", id);

        query = new WeaponProgressionQuery();

        data = queryExecutor.fetch(query.getStatement(), query::getOutput,
            params);

        if (data.iterator()
            .hasNext()) {
            result = data.iterator()
                .next();
        } else {
            result = new ImmutableWeaponProgression("",
                Collections.emptyList());
        }

        return result;
    }

}
