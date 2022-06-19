
package com.bernardomg.darksouls.explorer.item.misc.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.misc.domain.MiscItem;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface MiscItemService {

    public Iterable<? extends MiscItem> getAll(final Pagination pagination,
            final Sort sort);

    public Optional<? extends MiscItem> getOne(final Long id);

}
