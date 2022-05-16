
package com.bernardomg.darksouls.explorer.item.talisman.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.CatalystRequest;
import com.bernardomg.darksouls.explorer.item.talisman.domain.Talisman;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface TalismanService {

    public Iterable<? extends Talisman> getAll(final CatalystRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<? extends Talisman> getOne(final Long id);

}
