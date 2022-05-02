
package com.bernardomg.darksouls.explorer.item.weapon.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.request.WeaponRequest;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface WeaponService {

    public Iterable<Weapon> getAll(final WeaponRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<Weapon> getOne(final Long id);

    public Optional<WeaponProgression> getProgression(final Long id);

}
