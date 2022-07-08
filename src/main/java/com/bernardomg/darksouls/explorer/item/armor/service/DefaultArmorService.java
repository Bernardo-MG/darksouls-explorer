
package com.bernardomg.darksouls.explorer.item.armor.service;

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
    public final PageIterable<? extends Armor> getAll(final Pagination pagination, final Sort sort) {
        final Pageable              pageable;
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
        final Iterable<PersistentArmorLevel> levels;
        final Optional<ArmorProgression>     result;
        final Optional<? extends Armor>      armor;

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

    private final ArmorProgression toArmorProgression(final Iterable<? extends ArmorLevel> levels) {
        final String name;

        name = StreamSupport.stream(levels.spliterator(), false)
            .map(ArmorLevel::getName)
            .findAny()
            .orElse("");

        return new ImmutableArmorProgression(name, levels);
    }

}
