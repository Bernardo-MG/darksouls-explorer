
package com.bernardomg.darksouls.explorer.item.catalyst.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.CatalystRequest;
import com.bernardomg.darksouls.explorer.persistence.model.DefaultPageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@Service
public final class DefaultCatalystService implements CatalystService {

    @Autowired
    public DefaultCatalystService() {
        super();
    }

    @Override
    public final PageIterable<Catalyst> getAll(final CatalystRequest request,
            final Pagination pagination, final Sort sort) {
        return new DefaultPageIterable<>();
    }

    @Override
    public final Optional<Catalyst> getOne(final Long id) {
        return Optional.empty();
    }

}
