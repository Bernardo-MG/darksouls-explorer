
package com.bernardomg.darksouls.explorer.item.ammunition.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface AmmunitionService {

    public Iterable<? extends Ammunition> getAll(final ArmorRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<? extends Ammunition> getOne(final Long id);

}
