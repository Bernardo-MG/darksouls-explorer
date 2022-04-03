
package com.bernardomg.darksouls.explorer.item.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;

public interface ItemService {

    public Page<Item> getAll(final ItemRequest request, final Pageable page);

    public ArmorProgression getArmorLevels(final Long id);

    public Optional<Item> getOne(final Long id);

    public Page<ItemSource> getSources(final Long id, final Pageable page);

    public WeaponProgression getWeaponLevels(final Long id);

}
