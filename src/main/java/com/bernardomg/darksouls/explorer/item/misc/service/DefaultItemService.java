
package com.bernardomg.darksouls.explorer.item.misc.service;

import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.misc.domain.DtoItem;
import com.bernardomg.darksouls.explorer.item.misc.domain.Item;
import com.bernardomg.darksouls.explorer.item.misc.domain.PersistentItem;
import com.bernardomg.darksouls.explorer.item.misc.repository.ItemRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

@Service
public final class DefaultItemService implements ItemService {

    private final ItemRepository repository;

    @Autowired
    public DefaultItemService(final ItemRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends Item> getAll(final String type, final Pagination pagination, final Sort sort) {
        final Pageable   pageable;
        final Page<Item> page;

        pageable = Paginations.toSpring(pagination, sort);

        if (Strings.isBlank(type)) {
            page = repository.findAllSummaries(pageable);
        } else {
            page = repository.findAllSummaries(type, pageable);
        }

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Item> getOne(final Long id) {
        final Optional<PersistentItem> read;
        final PersistentItem           entity;
        final Optional<? extends Item> result;
        final DtoItem                  item;

        read = repository.findById(id);

        if (read.isPresent()) {

            entity = read.get();

            item = new DtoItem();
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
