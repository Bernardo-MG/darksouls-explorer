
package com.bernardomg.darksouls.explorer.item.weapon.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponSummary;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgression;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface WeaponService {

    public Iterable<? extends WeaponSummary> getAll(final Pagination pagination,
            final Sort sort);

    public Optional<? extends Weapon> getOne(final Long id);

    public Optional<WeaponProgression> getProgression(final Long id);

}
