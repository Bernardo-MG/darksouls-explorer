
package com.bernardomg.darksouls.explorer.item.key.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.key.domain.DtoKeyItem;
import com.bernardomg.darksouls.explorer.item.key.domain.KeyItem;
import com.bernardomg.darksouls.explorer.item.key.domain.PersistentKeyItem;
import com.bernardomg.darksouls.explorer.item.key.repository.KeyItemRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

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
        final Page<KeyItem> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends KeyItem> getOne(final Long id) {
        final Optional<PersistentKeyItem> read;
        final Optional<? extends KeyItem> result;
        final DtoKeyItem item;

        read = repository.findById(id);

        if (read.isPresent()) {
            item = new DtoKeyItem();

            BeanUtils.copyProperties(read.get(), item);

            result = Optional.of(item);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
