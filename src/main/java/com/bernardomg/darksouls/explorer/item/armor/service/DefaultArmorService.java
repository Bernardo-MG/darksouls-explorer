
package com.bernardomg.darksouls.explorer.item.armor.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.item.armor.query.ArmorsQuery;
import com.bernardomg.darksouls.explorer.item.itemdata.query.ArmorLevelQuery;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultArmorService implements ArmorService {

    private final Query<Armor>      allQuery        = new ArmorsQuery();

    private final Query<ArmorLevel> armorLevelQuery = new ArmorLevelQuery();

    private final QueryExecutor     queryExecutor;

    @Autowired
    public DefaultArmorService(final QueryExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<Armor> getAll(final ArmorRequest request,
            final Pagination pagination, final Sort sort) {
        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("name", request.getName());

        return queryExecutor.fetch(allQuery::getStatement, allQuery::getOutput,
            pagination, Arrays.asList(sort));
    }

    @Override
    public final Optional<Armor> getOne(final Long id) {
        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetchOne(allQuery::getStatement,
            allQuery::getOutput, params);
    }

    @Override
    public final Optional<ArmorProgression> getProgression(final Long id) {
        final Iterable<ArmorLevel> levels;
        final Optional<ArmorProgression> result;
        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        levels = queryExecutor.fetch(armorLevelQuery::getStatement,
            armorLevelQuery::getOutput, params);

        if (IterableUtils.isEmpty(levels)) {
            result = Optional.empty();
        } else {
            result = Optional.of(toArmorProgression(levels));
        }

        return result;
    }

    private final ArmorProgression
            toArmorProgression(final Iterable<ArmorLevel> levels) {
        final String name;

        name = StreamSupport.stream(levels.spliterator(), false)
            .map(ArmorLevel::getArmor)
            .findAny()
            .orElse("");

        return new ImmutableArmorProgression(name, levels);
    }

}
