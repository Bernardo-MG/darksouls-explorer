
package com.bernardomg.darksouls.explorer.item.shield.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentShield;
import com.bernardomg.darksouls.explorer.item.shield.domain.Shield;
import com.bernardomg.darksouls.explorer.item.shield.query.ShieldLevelQuery;
import com.bernardomg.darksouls.explorer.item.shield.repository.ShieldRepository;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultShieldService implements ShieldService {

    private final Query<WeaponLevel> levelQuery = new ShieldLevelQuery();

    private final QueryExecutor      queryExecutor;

    private final ShieldRepository   repository;

    @Autowired
    public DefaultShieldService(final ShieldRepository repo,
            final QueryExecutor queryExec) {
        super();

        repository = Objects.requireNonNull(repo);
        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<? extends Shield>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<PersistentShield> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Shield> getOne(final Long id) {
        return repository.findById(id);
    }

    @Override
    public final Optional<WeaponProgression> getProgression(final Long id) {
        final Iterable<WeaponLevel> levels;
        final Optional<WeaponProgression> result;
        final Map<String, Object> params;
        final Optional<? extends Shield> shield;

        shield = getOne(id);

        if (shield.isPresent()) {
            params = new HashMap<>();
            params.put("name", shield.get()
                .getName());

            levels = queryExecutor.fetch(levelQuery::getStatement,
                levelQuery::getOutput, params);

            if (IterableUtils.isEmpty(levels)) {
                result = Optional.empty();
            } else {
                result = Optional.of(toWeaponProgression(levels));
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    private final WeaponProgression
            toWeaponProgression(final Iterable<WeaponLevel> levels) {
        final String name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String> pathNames;
        Collection<WeaponLevel> currentLevels;
        WeaponProgressionPath path;

        pathNames = StreamSupport.stream(levels.spliterator(), false)
            .map(WeaponLevel::getPath)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        paths = new ArrayList<>();
        for (final String pathName : pathNames) {
            currentLevels = StreamSupport.stream(levels.spliterator(), false)
                .filter((l) -> pathName.equals(l.getPath()))
                .collect(Collectors.toList());

            path = new ImmutableWeaponProgressionPath(pathName, currentLevels);
            paths.add(path);
        }

        name = StreamSupport.stream(levels.spliterator(), false)
            .map(WeaponLevel::getWeapon)
            .findAny()
            .orElse("");

        return new ImmutableWeaponProgression(name, paths);
    }

}
