
package com.bernardomg.darksouls.explorer.item.misc.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.misc.domain.DtoMiscItem;
import com.bernardomg.darksouls.explorer.item.misc.domain.MiscItem;
import com.bernardomg.darksouls.explorer.item.misc.domain.PersistentMiscItem;
import com.bernardomg.darksouls.explorer.item.misc.repository.MiscItemRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

@Service
public final class DefaultMiscItemService implements MiscItemService {

    private final MiscItemRepository repository;

    @Autowired
    public DefaultMiscItemService(final MiscItemRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends MiscItem>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<MiscItem> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends MiscItem> getOne(final Long id) {
        final Optional<PersistentMiscItem> read;
        final Optional<? extends MiscItem> result;
        final DtoMiscItem item;

        read = repository.findById(id);

        if (read.isPresent()) {
            item = new DtoMiscItem();

            BeanUtils.copyProperties(read.get(), item);

            result = Optional.of(item);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
