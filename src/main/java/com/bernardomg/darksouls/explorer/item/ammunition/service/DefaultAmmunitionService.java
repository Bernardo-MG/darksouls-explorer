
package com.bernardomg.darksouls.explorer.item.ammunition.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.ammunition.domain.DtoAmmunition;
import com.bernardomg.darksouls.explorer.item.ammunition.domain.PersistentAmmunition;
import com.bernardomg.darksouls.explorer.item.ammunition.repository.AmmunitionRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

@Service
public final class DefaultAmmunitionService implements AmmunitionService {

    private final AmmunitionRepository repository;

    @Autowired
    public DefaultAmmunitionService(final AmmunitionRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<Summary> getAll(final Pagination pagination, final Sort sort) {
        final Pageable      pageable;
        final Page<Summary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<Ammunition> getOne(final Long id) {
        final Optional<PersistentAmmunition> read;
        final PersistentAmmunition           entity;
        final Optional<Ammunition>           result;
        final DtoAmmunition                  item;

        read = repository.findById(id);

        if (read.isPresent()) {
            item = new DtoAmmunition();

            entity = read.get();

            item.setId(entity.getId());
            item.setName(entity.getName());
            item.setDescription(entity.getDescription());
            item.setType(entity.getType());

            result = Optional.of(item);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
