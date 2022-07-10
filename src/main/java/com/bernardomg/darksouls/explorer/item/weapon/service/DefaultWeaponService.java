
package com.bernardomg.darksouls.explorer.item.weapon.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponBonus;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamage;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponDamageReduction;
import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.domain.PersistentWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.DtoWeaponAdjustment;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.DtoWeaponAdjustmentLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.PersistentWeaponAdjustment;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.WeaponAdjustment;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.WeaponAdjustmentLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.PersistentWeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponLevelNode;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.query.WeaponLevelQuery;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponAdjustmentRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;
import com.bernardomg.persistence.executor.Query;
import com.bernardomg.persistence.executor.QueryExecutor;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultWeaponService implements WeaponService {

    private final WeaponAdjustmentRepository adjustmentRepository;

    private final Query<WeaponLevelNode>     levelQuery = new WeaponLevelQuery();

    private final WeaponLevelRepository      levelRepository;

    private final QueryExecutor              queryExecutor;

    private final WeaponRepository           repository;

    @Autowired
    public DefaultWeaponService(final WeaponRepository repo, final WeaponLevelRepository levelRepo,
            final WeaponAdjustmentRepository adjustmentRepo, final QueryExecutor queryExec) {
        super();

        repository = Objects.requireNonNull(repo);
        levelRepository = Objects.requireNonNull(levelRepo);
        adjustmentRepository = Objects.requireNonNull(adjustmentRepo);
        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Optional<WeaponAdjustment> getAdjustment(final Long id) {
        final Optional<Weapon>                       weapon;
        final Collection<PersistentWeaponAdjustment> levelEntities;
        final Collection<WeaponAdjustmentLevel>      levels;
        final String                                 name;
        final DtoWeaponAdjustment                    adjustment;
        final Optional<WeaponAdjustment>             result;

        weapon = getOne(id);

        if (weapon.isPresent()) {
            name = weapon.get()
                .getName();

            levelEntities = adjustmentRepository.findAllByName(name);

            if (levelEntities.isEmpty()) {
                result = Optional.empty();
            } else {
                levels = levelEntities.stream()
                    .map(this::toWeaponAdjustment)
                    .collect(Collectors.toList());

                adjustment = new DtoWeaponAdjustment();
                adjustment.setName(name);
                adjustment.setLevels(levels);

                result = Optional.of(adjustment);
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final PageIterable<Summary> getAll(final String type, final Pagination pagination, final Sort sort) {
        final Pageable      pageable;
        final Page<Summary> page;

        pageable = Paginations.toSpring(pagination, sort);

        if (Strings.isBlank(type)) {
            page = repository.findAllSummaries(pageable);
        } else {
            page = repository.findAllSummaries(type, pageable);
        }

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<Weapon> getOne(final Long id) {
        final Optional<PersistentWeapon> read;
        final PersistentWeapon           entity;
        final Optional<Weapon>           result;
        final DtoWeapon                  weapon;
        final DtoWeaponRequirements      requirements;
        final DtoWeaponDamage            damage;
        final DtoWeaponDamageReduction   damageReduction;
        final DtoWeaponBonus             bonus;

        read = repository.findById(id);

        if (read.isPresent()) {
            entity = read.get();

            weapon = new DtoWeapon();
            weapon.setId(id);
            weapon.setName(entity.getName());
            weapon.setDescription(entity.getDescription());
            weapon.setDurability(entity.getDurability());
            weapon.setWeight(entity.getWeight());
            weapon.setStability(entity.getStability());
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
        final Collection<WeaponLevelNode>       levelNodes;
        final Optional<WeaponProgression>       result;
        final Map<String, Object>               params;
        final Optional<Weapon>                  weapon;
        final Collection<PersistentWeaponLevel> levels;
        final String                            name;

        weapon = getOne(id);

        if (weapon.isPresent()) {
            name = weapon.get()
                .getName();

            levels = levelRepository.findAllByName(name);

            if (IterableUtils.isEmpty(levels)) {
                result = Optional.empty();
            } else {
                params = new HashMap<>();
                params.put("name", name);

                levelNodes = queryExecutor.fetch(levelQuery::getStatement, levelQuery::getOutput, params);

                if (IterableUtils.isEmpty(levelNodes)) {
                    result = Optional.empty();
                } else {
                    result = Optional.of(toWeaponProgression(levelNodes, levels));
                }
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    private final WeaponAdjustmentLevel toWeaponAdjustment(final PersistentWeaponAdjustment entity) {
        final DtoWeaponAdjustmentLevel result;

        result = new DtoWeaponAdjustmentLevel();
        result.setId(entity.getId());
        result.setAdjustment(entity.getAdjustment());
        result.setFaith(entity.getFaith());
        result.setIntelligence(entity.getIntelligence());

        return result;
    }

    private final WeaponProgression toWeaponProgression(final Iterable<WeaponLevelNode> levelNodes,
            final Collection<PersistentWeaponLevel> levels) {
        final String                            name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String>                pathNames;
        final DtoWeaponProgression              result;
        Collection<WeaponProgressionLevel>      currentLevels;
        DtoWeaponProgressionPath                path;

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
            .map(PersistentWeaponLevel::getName)
            .findAny()
            .orElse("");

        result = new DtoWeaponProgression();
        result.setName(name);
        result.setPaths(paths);

        return result;
    }

    private final WeaponProgressionLevel toWeaponProgressionLevel(final PersistentWeaponLevel level,
            final Iterable<WeaponLevelNode> levelNodes) {
        final Optional<WeaponLevelNode> levelNodeFound;
        final WeaponLevelNode           levelNode;
        final DtoWeaponProgressionLevel result;
        final DtoWeaponBonus            bonus;
        final DtoWeaponDamage           damage;
        final DtoWeaponDamageReduction  damageReduction;

        result = new DtoWeaponProgressionLevel();

        result.setLevel(level.getLevel());
        result.setStability(level.getStability());

        bonus = new DtoWeaponBonus();
        bonus.setDexterity(level.getDexterityBonus());
        bonus.setFaith(level.getFaithBonus());
        bonus.setIntelligence(level.getIntelligenceBonus());
        bonus.setStrength(level.getStrengthBonus());
        result.setBonus(bonus);

        damage = new DtoWeaponDamage();
        damage.setCritical(level.getCritical());
        damage.setFire(level.getFireDamage());
        damage.setLightning(level.getLightningDamage());
        damage.setMagic(level.getMagicDamage());
        damage.setPhysical(level.getPhysicalDamage());

        result.setDamage(damage);

        damageReduction = new DtoWeaponDamageReduction();
        damageReduction.setFire(level.getFireReduction());
        damageReduction.setLightning(level.getLightningReduction());
        damageReduction.setMagic(level.getMagicReduction());
        damageReduction.setPhysical(level.getPhysicalReduction());

        result.setDamageReduction(damageReduction);

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
