
package com.bernardomg.darksouls.explorer.item.weapon.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponSummary;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface WeaponService {

    public Iterable<? extends WeaponSummary> getAll(final Pagination pagination,
            final Sort sort);

    public Optional<? extends Weapon> getOne(final Long id);

    public Optional<WeaponProgression> getProgression(final Long id);

}
