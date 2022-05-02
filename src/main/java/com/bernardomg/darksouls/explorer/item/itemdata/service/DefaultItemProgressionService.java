
package com.bernardomg.darksouls.explorer.item.itemdata.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.item.itemdata.query.ArmorLevelQuery;
import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.query.WeaponLevelQuery;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultItemProgressionService
        implements ItemProgressionService {

    private final Query<ArmorLevel>     armorLevelQuery  = new ArmorLevelQuery();

    private final QueryExecutor<String> queryExecutor;

    private final Query<WeaponLevel>    weaponLevelQuery = new WeaponLevelQuery();

    @Autowired
    public DefaultItemProgressionService(
            final QueryExecutor<String> queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Optional<ArmorProgression> getArmorProgression(final Long id) {
        final Iterable<ArmorLevel> levels;
        final Optional<ArmorProgression> result;

        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        levels = queryExecutor.fetch(armorLevelQuery.getStatement(),
            armorLevelQuery::getOutput, params);

        if (IterableUtils.isEmpty(levels)) {
            result = Optional.empty();
        } else {
            result = Optional.of(toArmorProgression(levels));
        }

        return result;
    }

    @Override
    public final Optional<WeaponProgression>
            getWeaponProgression(final Long id) {
        final Iterable<WeaponLevel> levels;
        final Optional<WeaponProgression> result;

        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        levels = queryExecutor.fetch(weaponLevelQuery.getStatement(),
            weaponLevelQuery::getOutput, params);

        if (IterableUtils.isEmpty(levels)) {
            result = Optional.empty();
        } else {
            result = Optional.of(toWeaponProgression(levels));
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
