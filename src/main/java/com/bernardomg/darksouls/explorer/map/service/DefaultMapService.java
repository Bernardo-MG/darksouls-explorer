
package com.bernardomg.darksouls.explorer.map.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.query.MapQueries;

@Component
public final class DefaultMapService implements MapService {

    private final MapQueries queries;

    public DefaultMapService(final MapQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Page<Map> getAll(final Pageable page) {
        return queries.findAll(page);
    }

}
