
package com.bernardomg.darksouls.explorer.item.spell.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.item.spell.domain.Spell;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface SpellService {

    public Iterable<? extends Spell> getAll(final ArmorRequest request,
            final Pagination pagination, final Sort sort);

    public Optional<? extends Spell> getOne(final Long id);

}
