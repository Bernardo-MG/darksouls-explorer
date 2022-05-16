
package com.bernardomg.darksouls.explorer.item.weapon.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.PersistentWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.request.WeaponRequest;
import com.bernardomg.darksouls.explorer.item.weapon.query.WeaponLevelQuery;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultWeaponService implements WeaponService {

    private final QueryExecutor      queryExecutor;

    private final WeaponRepository   repository;

    private final Query<WeaponLevel> weaponLevelQuery = new WeaponLevelQuery();

    @Autowired
    public DefaultWeaponService(final WeaponRepository repo,
            final QueryExecutor queryExec) {
        super();

        repository = Objects.requireNonNull(repo);
        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<? extends Weapon> getAll(
            final WeaponRequest request, final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;
        final Page<PersistentWeapon> page;

        if (pagination.getPaged()) {
            pageable = PageRequest.of(pagination.getPage(),
                pagination.getSize());
        } else {
            pageable = Pageable.unpaged();
        }

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Weapon> getOne(final Long id) {
        return repository.findById(id);
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
