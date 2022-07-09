
package com.bernardomg.darksouls.explorer.item.armor.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorSummary;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface ArmorService {

    public Iterable<ArmorSummary> getAll(final Pagination pagination, final Sort sort);

    public Optional<Armor> getOne(final Long id);

    public Optional<ArmorProgression> getProgression(final Long id);

}
