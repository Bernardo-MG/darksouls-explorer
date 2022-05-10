
package com.bernardomg.darksouls.explorer.item.weapon.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.request.WeaponRequest;
import com.bernardomg.darksouls.explorer.item.weapon.query.WeaponLevelQuery;
import com.bernardomg.darksouls.explorer.item.weapon.query.WeaponsQuery;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultWeaponService implements WeaponService {

    private final Query<Weapon>      allQuery         = new WeaponsQuery();

    private final QueryExecutor      queryExecutor;

    private final Query<WeaponLevel> weaponLevelQuery = new WeaponLevelQuery();

    @Autowired
    public DefaultWeaponService(final QueryExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<Weapon> getAll(final WeaponRequest request,
            final Pagination pagination, final Sort sort) {
        return queryExecutor.fetch(allQuery::getStatement, allQuery::getOutput,
            pagination, Arrays.asList(sort));
    }

    @Override
    public final Optional<Weapon> getOne(final Long id) {
        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetchOne(allQuery::getStatement,
            allQuery::getOutput, params);
    }

    @Override
    public final Optional<WeaponProgression> getProgression(final Long id) {
        final Iterable<WeaponLevel> levels;
        final Optional<WeaponProgression> result;

        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        levels = queryExecutor.fetch(weaponLevelQuery::getStatement,
            weaponLevelQuery::getOutput, params);

        if (IterableUtils.isEmpty(levels)) {
            result = Optional.empty();
        } else {
            result = Optional.of(toWeaponProgression(levels));
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