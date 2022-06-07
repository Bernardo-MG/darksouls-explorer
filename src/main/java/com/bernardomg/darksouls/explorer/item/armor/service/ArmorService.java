
package com.bernardomg.darksouls.explorer.item.armor.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface ArmorService {

    public Iterable<? extends Armor> getAll(final Pagination pagination,
            final Sort sort);

    public Optional<? extends Armor> getOne(final Long id);

    public Optional<ArmorProgression> getProgression(final Long id);

}
