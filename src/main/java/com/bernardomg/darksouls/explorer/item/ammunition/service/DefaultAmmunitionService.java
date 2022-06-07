
package com.bernardomg.darksouls.explorer.item.ammunition.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.ammunition.domain.PersistentAmmunition;
import com.bernardomg.darksouls.explorer.item.ammunition.repository.AmmunitionRepository;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@Service
public final class DefaultAmmunitionService implements AmmunitionService {

    private final AmmunitionRepository repository;

    @Autowired
    public DefaultAmmunitionService(final AmmunitionRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends Ammunition>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<PersistentAmmunition> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Ammunition> getOne(final Long id) {
        return repository.findById(id);
    }

}
