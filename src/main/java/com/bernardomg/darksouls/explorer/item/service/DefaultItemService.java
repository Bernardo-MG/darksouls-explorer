
package com.bernardomg.darksouls.explorer.item.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.query.ItemQueries;
import com.bernardomg.darksouls.explorer.item.query.WeaponQueries;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;

@Service
public final class DefaultItemService implements ItemService {

    private final ItemQueries   itemQueries;

    private final WeaponQueries weaponQueries;

    @Autowired
    public DefaultItemService(final ItemQueries itemq,
            final WeaponQueries wqueries) {
        super();

        itemQueries = Objects.requireNonNull(itemq);
        weaponQueries = Objects.requireNonNull(wqueries);
    }

    @Override
    public final Page<Item> getAll(final ItemRequest request,
            final Pageable page) {
        return itemQueries.findAll(request.getName(), request.getTags(), page);
    }

    @Override
    public final Page<ItemSource> getSources(final Long id,
            final Pageable page) {
        return itemQueries.findSources(id, page);
    }

    @Override
    public final WeaponProgression getWeaponLevels(final Long id) {
        return weaponQueries.findWeaponProgression(id);
    }

}
