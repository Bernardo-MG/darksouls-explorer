
package com.bernardomg.darksouls.explorer.item.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.query.AllItemsQuery;
import com.bernardomg.darksouls.explorer.item.query.ItemSourcesQuery;
import com.bernardomg.darksouls.explorer.item.query.WeaponProgressionQuery;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;
import com.bernardomg.darksouls.explorer.persistence.DslQuery;
import com.bernardomg.darksouls.explorer.persistence.QueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.TextQuery;

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
        final TextQuery<Map<String, Object>, ItemSource> query;

        query = new ItemSourcesQuery();

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetch(query, params, page);
    }

    @Override
    public final WeaponProgression getWeaponLevels(final Long id) {
        final Map<String, Object> params;
        final TextQuery<Map<String, Object>, Map<String, Object>> query;
        final Iterable<Map<String, Object>> data;

        params = new HashMap<>();
        params.put("id", id);

        query = new WeaponProgressionQuery();

        data = queryExecutor.fetch(query, params);

        return getOutput(data);
    }

    private final WeaponProgression
            getOutput(final Iterable<Map<String, Object>> record) {
        final String name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String> pathNames;
        Collection<WeaponLevel> levels;
        WeaponProgressionPath path;

        pathNames = StreamSupport.stream(record.spliterator(), false)
            .map((data) -> (String) data.getOrDefault("path", ""))
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        paths = new ArrayList<>();
        for (final String pathName : pathNames) {
            levels = StreamSupport.stream(record.spliterator(), false)
                .filter(
                    (data) -> pathName.equals(data.getOrDefault("path", "")))
                .map(this::toWeaponLevel)
                .collect(Collectors.toList());

            path = new ImmutableWeaponProgressionPath(pathName, levels);
            paths.add(path);
        }

        name = StreamSupport.stream(record.spliterator(), false)
            .map((data) -> (String) data.getOrDefault("weapon", ""))
            .findAny()
            .orElse("");

        return new ImmutableWeaponProgression(name, paths);
    }

    private final WeaponLevel toWeaponLevel(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
            ((Long) record.getOrDefault("level", 0l)).intValue(),
            ((Long) record.getOrDefault("pathLevel", 0l)).intValue(),
            ((Long) record.getOrDefault("physicalDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("magicDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("fireDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("lightningDamage", 0l)).intValue());
    }

}
