
package com.bernardomg.darksouls.explorer.item.catalyst.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.CatalystSummary;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface CatalystService {

    public Iterable<? extends CatalystSummary>
            getAll(final Pagination pagination, final Sort sort);

    public Optional<? extends Catalyst> getOne(final Long id);

}
