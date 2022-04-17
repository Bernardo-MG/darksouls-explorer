
package com.bernardomg.darksouls.explorer.item.service;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface ItemService {

    public PageIterable<Item> getAll(final ItemRequest request,
            final Pagination pagination, final Sort sort);

    public ArmorProgression getArmorLevels(final Long id);

    public Item getOne(final Long id);

    public PageIterable<ItemSource> getSources(final Long id,
            final Pagination pagination, final Sort sort);

    public WeaponProgression getWeaponLevels(final Long id);

}
