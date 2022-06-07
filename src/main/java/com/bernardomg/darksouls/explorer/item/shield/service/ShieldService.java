
package com.bernardomg.darksouls.explorer.item.shield.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.shield.domain.Shield;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface ShieldService {

    public Iterable<? extends Shield> getAll(final Pagination pagination,
            final Sort sort);

    public Optional<? extends Shield> getOne(final Long id);

    public Optional<WeaponProgression> getProgression(final Long id);

}
