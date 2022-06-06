
package com.bernardomg.darksouls.explorer.item.key.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.item.key.domain.KeyItem;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface KeyItemService {

    public Iterable<? extends KeyItem> getAll(final ArmorRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<? extends KeyItem> getOne(final Long id);

}
