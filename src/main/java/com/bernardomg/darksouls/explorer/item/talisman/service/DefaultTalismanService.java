
package com.bernardomg.darksouls.explorer.item.talisman.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.talisman.domain.DtoTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.PersistentTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.Talisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.TalismanSummary;
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
    public final PageIterable<? extends TalismanSummary>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<TalismanSummary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Talisman> getOne(final Long id) {
        final Optional<PersistentTalisman> read;
        final Optional<? extends Talisman> result;
        final DtoTalisman talisman;

        read = repository.findById(id);

        if (read.isPresent()) {
            talisman = new DtoTalisman();

            BeanUtils.copyProperties(read.get(), talisman);
            BeanUtils.copyProperties(read.get(), talisman.getRequirements());

            result = Optional.of(talisman);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
