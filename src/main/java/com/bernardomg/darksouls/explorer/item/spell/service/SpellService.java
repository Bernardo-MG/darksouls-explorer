
package com.bernardomg.darksouls.explorer.item.spell.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.spell.domain.Spell;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface SpellService {

    public Iterable<Summary> getAll(final String school, final Pagination pagination, final Sort sort);

    public Optional<Spell> getOne(final Long id);

}
