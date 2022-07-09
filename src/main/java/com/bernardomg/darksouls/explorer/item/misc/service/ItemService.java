
package com.bernardomg.darksouls.explorer.item.misc.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.misc.domain.Item;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface ItemService {

    public Iterable<Item> getAll(final String type, final Pagination pagination, final Sort sort);

    public Optional<Item> getOne(final Long id);

}
