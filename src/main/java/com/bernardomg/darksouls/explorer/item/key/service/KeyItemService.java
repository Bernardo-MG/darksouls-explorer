
package com.bernardomg.darksouls.explorer.item.key.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.key.domain.KeyItem;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface KeyItemService {

    public Iterable<? extends KeyItem> getAll(final Pagination pagination,
            final Sort sort);

    public Optional<? extends KeyItem> getOne(final Long id);

}
