
package com.bernardomg.darksouls.explorer.item.shield.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentShield;
import com.bernardomg.darksouls.explorer.item.shield.domain.Shield;
import com.bernardomg.darksouls.explorer.item.shield.domain.request.ShieldRequest;
import com.bernardomg.darksouls.explorer.item.shield.shield.ShieldRepository;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@Service
public final class DefaultShieldService implements ShieldService {

    private final ShieldRepository repository;

    @Autowired
    public DefaultShieldService(final ShieldRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends Shield> getAll(
            final ShieldRequest request, final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;
        final Page<PersistentShield> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Shield> getOne(final Long id) {
        return repository.findById(id);
    }

}
