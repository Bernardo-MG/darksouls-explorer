
package com.bernardomg.darksouls.explorer.item.weapon.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.domain.PersistentWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponSummary;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.PersistentWeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponLevelNode;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.query.WeaponLevelQuery;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;
import com.bernardomg.persistence.executor.Query;
import com.bernardomg.persistence.executor.QueryExecutor;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

public abstract class AbstractWeaponService implements WeaponService {

    private final Query<WeaponLevelNode> levelQuery = new WeaponLevelQuery();

    private final WeaponLevelRepository  levelRepository;

    private final QueryExecutor          queryExecutor;

    private final WeaponRepository       repository;

    private final String                 type;

    public AbstractWeaponService(final WeaponRepository repo,
            final WeaponLevelRepository levelRepo,
            final QueryExecutor queryExec, final String tp) {
        super();

        repository = Objects.requireNonNull(repo);
        levelRepository = Objects.requireNonNull(levelRepo);
        queryExecutor = Objects.requireNonNull(queryExec);
        type = Objects.requireNonNull(tp);
    }

    @Override
    public final PageIterable<? extends WeaponSummary>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<WeaponSummary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(type, pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Weapon> getOne(final Long id) {
        final Optional<PersistentWeapon> read;
        final PersistentWeapon entity;
        final Optional<? extends Weapon> result;
        final DtoWeapon weapon;
        final DtoWeaponRequirements requirements;
        final DtoWeaponDamage damage;
        final DtoWeaponDamageReduction damageReduction;
        final DtoWeaponBonus bonus;

        read = repository.findById(id);

        if (read.isPresent()) {
            weapon = new DtoWeapon();
            entity = read.get();

            weapon.setId(id);
            weapon.setName(entity.getName());
            weapon.setDescription(entity.getDescription());
            weapon.setDurability(entity.getDurability());
            weapon.setWeight(entity.getWeight());
            weapon.setType(entity.getType());
            weapon.setSubtype(entity.getSubtype());

            requirements = new DtoWeaponRequirements();
            requirements.setDexterity(entity.getDexterity());
            requirements.setFaith(entity.getFaith());
            requirements.setIntelligence(entity.getIntelligence());
            requirements.setStrength(entity.getStrength());
            weapon.setRequirements(requirements);

            damage = new DtoWeaponDamage();
            damage.setFire(entity.getFireDamage());
            damage.setLightning(entity.getLightningDamage());
            damage.setMagic(entity.getMagicDamage());
            damage.setPhysical(entity.getPhysicalDamage());
            damage.setCritical(entity.getCriticalDamage());
            weapon.setDamage(damage);

            damageReduction = new DtoWeaponDamageReduction();
            damageReduction.setFire(entity.getFireReduction());
            damageReduction.setLightning(entity.getLightningReduction());
            damageReduction.setMagic(entity.getMagicReduction());
            damageReduction.setPhysical(entity.getPhysicalReduction());
            weapon.setDamageReduction(damageReduction);

            bonus = new DtoWeaponBonus();
            bonus.setDexterity(entity.getDexterityBonus());
            bonus.setFaith(entity.getFaithBonus());
            bonus.setIntelligence(entity.getIntelligenceBonus());
            bonus.setStrength(entity.getStrengthBonus());
            weapon.setBonus(bonus);

            result = Optional.of(weapon);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Optional<WeaponProgression> getProgression(final Long id) {
        final Collection<WeaponLevelNode> levelNodes;
        final Optional<WeaponProgression> result;
        final Map<String, Object> params;
        final Optional<? extends Weapon> weapon;
        final Iterable<String> names;
        final Collection<PersistentWeaponLevel> levels;

        weapon = getOne(id);

        if (weapon.isPresent()) {
            params = new HashMap<>();
            params.put("name", weapon.get()
                .getName());

            levelNodes = queryExecutor.fetch(levelQuery::getStatement,
                levelQuery::getOutput, params);

            names = levelNodes.stream()
                .map(WeaponLevelNode::getName)
                .collect(Collectors.toList());
            levels = levelRepository.findAllForNames(names);

            if (IterableUtils.isEmpty(levels)) {
                result = Optional.empty();
            } else {
                result = Optional.of(toWeaponProgression(levelNodes, levels));
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    private final WeaponProgression toWeaponProgression(
            final Iterable<WeaponLevelNode> levelNodes,
            final Collection<PersistentWeaponLevel> levels) {
        final String name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String> pathNames;
        final DtoWeaponProgression result;
        Collection<WeaponProgressionLevel> currentLevels;
        DtoWeaponProgressionPath path;

        pathNames = StreamSupport.stream(levelNodes.spliterator(), false)
            .map(WeaponLevelNode::getPath)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        paths = new ArrayList<>();
        for (final String pathName : pathNames) {
            currentLevels = StreamSupport.stream(levels.spliterator(), false)
                .filter((l) -> pathName.equals(l.getPath()))
                .map((l) -> toWeaponProgressionLevel(l, levelNodes))
                .collect(Collectors.toList());

            path = new DtoWeaponProgressionPath();
            path.setPath(pathName);
            path.setLevels(currentLevels);
            paths.add(path);
        }

        name = StreamSupport.stream(levels.spliterator(), false)
            .map(WeaponLevel::getName)
            .findAny()
            .orElse("");

        result = new DtoWeaponProgression();
        result.setName(name);
        result.setPaths(paths);

        return result;
    }

    private final WeaponProgressionLevel toWeaponProgressionLevel(
            final WeaponLevel level,
            final Iterable<WeaponLevelNode> levelNodes) {
        final Optional<WeaponLevelNode> levelNodeFound;
        final WeaponLevelNode levelNode;
        final DtoWeaponProgressionLevel result;

        result = new DtoWeaponProgressionLevel();
        // TODO: Avoid copying like this
        BeanUtils.copyProperties(level, result);

        levelNodeFound = StreamSupport.stream(levelNodes.spliterator(), false)
            .filter(n -> n.getName()
                .equals(level.getName())
                    && n.getPath()
                        .equals(level.getPath())
                    && n.getLevel()
                        .equals(level.getLevel()))
            .findFirst();
        if (levelNodeFound.isPresent()) {
            levelNode = levelNodeFound.get();
            result.setPathLevel(levelNode.getPathLevel());
        } else {
            // TODO: Error
        }

        return result;
    }

}
