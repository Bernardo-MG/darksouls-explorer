
package com.bernardomg.darksouls.explorer.item.key.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.key.domain.KeyItem;
import com.bernardomg.darksouls.explorer.item.key.domain.PersistentKeyItem;
import com.bernardomg.darksouls.explorer.item.key.repository.KeyItemRepository;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@Service
public final class DefaultKeyItemService implements KeyItemService {

    private final KeyItemRepository repository;

    @Autowired
    public DefaultKeyItemService(final KeyItemRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends KeyItem>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<PersistentKeyItem> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends KeyItem> getOne(final Long id) {
        return repository.findById(id);
    }

}
