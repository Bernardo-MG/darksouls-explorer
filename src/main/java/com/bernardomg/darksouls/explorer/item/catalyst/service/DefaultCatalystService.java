
package com.bernardomg.darksouls.explorer.item.catalyst.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.CatalystSummary;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.DtoCatalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.PersistentCatalyst;
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
    public final PageIterable<? extends CatalystSummary>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<CatalystSummary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Catalyst> getOne(final Long id) {
        final Optional<PersistentCatalyst> read;
        final Optional<? extends Catalyst> result;
        final DtoCatalyst talisman;

        read = repository.findById(id);

        if (read.isPresent()) {
            talisman = new DtoCatalyst();

            BeanUtils.copyProperties(read.get(), talisman);
            BeanUtils.copyProperties(read.get(), talisman.getRequirements());

            result = Optional.of(talisman);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
