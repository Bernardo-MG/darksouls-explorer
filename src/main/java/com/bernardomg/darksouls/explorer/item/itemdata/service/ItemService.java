
package com.bernardomg.darksouls.explorer.item.itemdata.service;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.Item;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.request.ItemRequest;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface ItemService {

    public Iterable<Item> getAll(final ItemRequest request,
            final Pagination pagination, final Sort sort);

    public Item getOne(final Long id);

    public Iterable<ItemSource> getSources(final Long id,
            final Pagination pagination, final Sort sort);

}
