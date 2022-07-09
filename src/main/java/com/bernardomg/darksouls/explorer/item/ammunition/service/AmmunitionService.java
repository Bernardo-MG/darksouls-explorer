
package com.bernardomg.darksouls.explorer.item.ammunition.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface AmmunitionService {

    public Iterable<Ammunition> getAll(final Pagination pagination, final Sort sort);

    public Optional<Ammunition> getOne(final Long id);

}
