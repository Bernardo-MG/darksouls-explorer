
package com.bernardomg.darksouls.explorer.item.talisman.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.CatalystRequest;
import com.bernardomg.darksouls.explorer.item.talisman.domain.PersistentTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.Talisman;
import com.bernardomg.darksouls.explorer.item.talisman.repository.TalismanRepository;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@Service
public final class DefaultTalismanService implements TalismanService {

    private final TalismanRepository repository;

    @Autowired
    public DefaultTalismanService(final TalismanRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends Talisman> getAll(
            final CatalystRequest request, final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;
        final Page<PersistentTalisman> page;

        if (pagination.getPaged()) {
            pageable = PageRequest.of(pagination.getPage(),
                pagination.getSize());
        } else {
            pageable = Pageable.unpaged();
        }

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Talisman> getOne(final Long id) {
        return repository.findById(id);
    }

}
