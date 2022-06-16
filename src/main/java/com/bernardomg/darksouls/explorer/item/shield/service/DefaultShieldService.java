
package com.bernardomg.darksouls.explorer.item.shield.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.DtoWeaponLevelNode;
import com.bernardomg.darksouls.explorer.item.domain.DtoWeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.PersistentWeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevelNode;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.shield.domain.DtoShield;
import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentShield;
import com.bernardomg.darksouls.explorer.item.shield.domain.Shield;
import com.bernardomg.darksouls.explorer.item.shield.domain.ShieldSummary;
import com.bernardomg.darksouls.explorer.item.shield.query.ShieldLevelQuery;
import com.bernardomg.darksouls.explorer.item.shield.repository.ShieldRepository;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultShieldService implements ShieldService {

    private final Query<DtoWeaponLevelNode> levelQuery = new ShieldLevelQuery();

    private final WeaponLevelRepository     levelRepository;

    private final QueryExecutor             queryExecutor;

    private final ShieldRepository          repository;

    @Autowired
    public DefaultShieldService(final ShieldRepository repo,
            final WeaponLevelRepository levelRepo,
            final QueryExecutor queryExec) {
        super();

        repository = Objects.requireNonNull(repo);
        levelRepository = Objects.requireNonNull(levelRepo);
        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<? extends ShieldSummary>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<ShieldSummary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Shield> getOne(final Long id) {
        final Optional<PersistentShield> read;
        final PersistentShield entity;
        final Optional<? extends Shield> result;
        final DtoShield weapon;
        final DtoWeaponRequirements requirements;
        final DtoWeaponDamage damage;
        final DtoWeaponDamageReduction damageReduction;
        final DtoWeaponBonus bonus;

        read = repository.findById(id);

        if (read.isPresent()) {
            weapon = new DtoShield();
            entity = read.get();

            weapon.setId(id);
            weapon.setName(entity.getName());
            weapon.setDescription(entity.getDescription());
            weapon.setDurability(entity.getDurability());
            weapon.setWeight(entity.getWeight());

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
        final Optional<? extends Shield> weapon;
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
        Collection<WeaponProgressionLevel> currentLevels;
        WeaponProgressionPath path;

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

            path = new ImmutableWeaponProgressionPath(pathName, currentLevels);
            paths.add(path);
        }

        name = StreamSupport.stream(levels.spliterator(), false)
            .map(WeaponLevel::getName)
            .findAny()
            .orElse("");

        return new ImmutableWeaponProgression(name, paths);
    }

    private final WeaponProgressionLevel toWeaponProgressionLevel(
            final WeaponLevel level,
            final Iterable<WeaponLevelNode> levelNodes) {
        final Optional<WeaponLevelNode> levelNodeFound;
        final WeaponLevelNode levelNode;
        final DtoWeaponProgressionLevel result;

        result = new DtoWeaponProgressionLevel();
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
