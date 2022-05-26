
package com.bernardomg.darksouls.explorer.item.armor.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmor;
import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.item.armor.query.ArmorLevelQuery;
import com.bernardomg.darksouls.explorer.item.armor.repository.ArmorRepository;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultArmorService implements ArmorService {

    private final Query<ArmorLevel> armorLevelQuery = new ArmorLevelQuery();

    private final QueryExecutor     queryExecutor;

    private final ArmorRepository   repository;

    @Autowired
    public DefaultArmorService(final ArmorRepository repo,
            final QueryExecutor queryExec) {
        super();

        repository = Objects.requireNonNull(repo);
        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<? extends Armor> getAll(
            final ArmorRequest request, final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;
        final Page<PersistentArmor> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Armor> getOne(final Long id) {
        return repository.findById(id);
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
