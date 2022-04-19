
package com.bernardomg.darksouls.explorer.item.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
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

    private final QueryExecutor<String>          queryExecutor;

    private final Query<List<ArmorProgression>>  armorProgressionQuery  = new ArmorProgressionQuery();

    private final Query<List<WeaponProgression>> weaponProgressionQuery = new WeaponProgressionQuery();

    @Autowired
    public DefaultItemProgressionService(
            final QueryExecutor<String> queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Optional<ArmorProgression> getArmorProgression(final Long id) {
        return read(id, armorProgressionQuery);
    }

    @Override
    public final Optional<WeaponProgression>
            getWeaponProgression(final Long id) {
        return read(id, weaponProgressionQuery);
    }

    private final <T> Optional<T> read(final Long id,
            final Query<List<T>> query) {
        final Map<String, Object> params;
        final Iterable<T> data;
        final Optional<T> result;
        final T progression;

        params = new HashMap<>();
        params.put("id", id);

        data = queryExecutor.fetch(query.getStatement(), query::getOutput,
            params);

        if (!IterableUtils.isEmpty(data)) {
            progression = data.iterator()
                .next();
            result = Optional.of(progression);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
