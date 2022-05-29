
package com.bernardomg.darksouls.explorer.item.misc.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.item.misc.domain.MiscItem;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface MiscItemService {

    public Iterable<? extends MiscItem> getAll(final ArmorRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<? extends MiscItem> getOne(final Long id);

}
