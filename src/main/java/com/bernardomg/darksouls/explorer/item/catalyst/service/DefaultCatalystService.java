
package com.bernardomg.darksouls.explorer.item.catalyst.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.PersistentCatalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.CatalystRequest;
import com.bernardomg.darksouls.explorer.item.catalyst.repository.CatalystRepository;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@Service
public final class DefaultCatalystService implements CatalystService {

    private final CatalystRepository repository;

    @Autowired
    public DefaultCatalystService(final CatalystRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends Catalyst> getAll(
            final CatalystRequest request, final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;
        final Page<PersistentCatalyst> page;

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
    public final Optional<? extends Catalyst> getOne(final Long id) {
        return repository.findById(id);
    }

}
