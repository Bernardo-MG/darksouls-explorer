
package com.bernardomg.darksouls.explorer.item.shield.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.shield.domain.Spell;
import com.bernardomg.darksouls.explorer.item.shield.domain.request.ShieldRequest;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface ShieldService {

    public Iterable<? extends Spell> getAll(final ShieldRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<? extends Spell> getOne(final Long id);

    public Optional<WeaponProgression> getProgression(final Long id);

}
