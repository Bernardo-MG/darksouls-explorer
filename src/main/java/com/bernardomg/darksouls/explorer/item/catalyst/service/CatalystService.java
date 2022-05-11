
package com.bernardomg.darksouls.explorer.item.catalyst.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.CatalystRequest;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface CatalystService {

    public Iterable<Catalyst> getAll(final CatalystRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<Catalyst> getOne(final Long id);

}
