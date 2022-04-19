
package com.bernardomg.darksouls.explorer.item.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.query.ArmorProgressionQuery;
import com.bernardomg.darksouls.explorer.item.query.WeaponProgressionQuery;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

@Service
public final class DefaultItemProgressionService
        implements ItemProgressionService {

    private final QueryExecutor<String> queryExecutor;

    @Autowired
    public DefaultItemProgressionService(
            final QueryExecutor<String> queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Optional<ArmorProgression> getArmorProgression(final Long id) {
        final Map<String, Object> params;
        final Query<List<ArmorProgression>> query;
        final Iterable<ArmorProgression> data;
        final Optional<ArmorProgression> result;
        final ArmorProgression progression;

        params = new HashMap<>();
        params.put("id", id);

        query = new ArmorProgressionQuery();

        data = queryExecutor.fetch(query.getStatement(), query::getOutput,
            params);

        if (data.iterator()
            .hasNext()) {
            progression = data.iterator()
                .next();
            result = Optional.of(progression);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Optional<WeaponProgression>
            getWeaponProgression(final Long id) {
        final Map<String, Object> params;
        final Query<List<WeaponProgression>> query;
        final Iterable<WeaponProgression> data;
        final Optional<WeaponProgression> result;
        final WeaponProgression progression;

        params = new HashMap<>();
        params.put("id", id);

        query = new WeaponProgressionQuery();

        data = queryExecutor.fetch(query.getStatement(), query::getOutput,
            params);

        if (data.iterator()
            .hasNext()) {
            progression = data.iterator()
                .next();
            result = Optional.of(progression);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
