
package com.bernardomg.darksouls.explorer.item.armor.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmor;
import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.repository.ArmorLevelRepository;
import com.bernardomg.darksouls.explorer.item.armor.repository.ArmorRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

import liquibase.repackaged.org.apache.commons.collections4.IterableUtils;

@Service
public final class DefaultArmorService implements ArmorService {

    private final ArmorLevelRepository levelRepository;

    private final ArmorRepository      repository;

    @Autowired
    public DefaultArmorService(final ArmorRepository repo, final ArmorLevelRepository levelRepo) {
        super();

        repository = Objects.requireNonNull(repo);
        levelRepository = Objects.requireNonNull(levelRepo);
    }

    @Override
    public final PageIterable<Summary> getAll(final Pagination pagination, final Sort sort) {
        final Pageable      pageable;
        final Page<Summary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<Armor> getOne(final Long id) {
        final Optional<PersistentArmor> read;
        final PersistentArmor           entity;
        final Optional<Armor>           result;
        final DtoArmor                  armor;

        read = repository.findById(id);

        if (read.isPresent()) {
            entity = read.get();

            armor = new DtoArmor();
            armor.setId(entity.getId());
            armor.setName(entity.getName());
            armor.setDescription(entity.getDescription());
            armor.setDurability(entity.getDurability());
            armor.setWeight(entity.getWeight());

            result = Optional.of(armor);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Optional<ArmorProgression> getProgression(final Long id) {
        final Iterable<PersistentArmorLevel> levels;
        final Optional<ArmorProgression>     result;
        final Optional<Armor>                armor;

        armor = getOne(id);

        if (armor.isPresent()) {
            levels = levelRepository.findAllByName(armor.get()
                .getName());

            if (IterableUtils.isEmpty(levels)) {
                result = Optional.empty();
            } else {
                result = Optional.of(toArmorProgression(levels));
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    private final ArmorLevel toArmorLevel(final PersistentArmorLevel entity) {
        final DtoArmorLevel armorLevel;

        armorLevel = new DtoArmorLevel();
        armorLevel.setLevel(entity.getLevel());
        armorLevel.setName(entity.getName());
        armorLevel.setBleedProtection(entity.getBleedProtection());
        armorLevel.setCurseProtection(entity.getCurseProtection());
        armorLevel.setFireProtection(entity.getFireProtection());
        armorLevel.setLightningProtection(entity.getLightningProtection());
        armorLevel.setMagicProtection(entity.getMagicProtection());
        armorLevel.setPoisonProtection(entity.getPoisonProtection());
        armorLevel.setRegularProtection(entity.getRegularProtection());
        armorLevel.setSlashProtection(entity.getSlashProtection());
        armorLevel.setStrikeProtection(entity.getStrikeProtection());
        armorLevel.setThrustProtection(entity.getThrustProtection());

        return armorLevel;
    }

    private final ArmorProgression toArmorProgression(final Iterable<PersistentArmorLevel> entities) {
        final String                         name;
        final Iterable<? extends ArmorLevel> levels;

        name = StreamSupport.stream(entities.spliterator(), false)
            .map(PersistentArmorLevel::getName)
            .findAny()
            .orElse("");
        levels = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toArmorLevel)
            .collect(Collectors.toList());

        return new ImmutableArmorProgression(name, levels);
    }

}
