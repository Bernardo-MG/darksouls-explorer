
package com.bernardomg.darksouls.explorer.item.spell.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.spell.domain.DtoSpell;
import com.bernardomg.darksouls.explorer.item.spell.domain.PersistentSpell;
import com.bernardomg.darksouls.explorer.item.spell.domain.Spell;
import com.bernardomg.darksouls.explorer.item.spell.domain.SpellSummary;
import com.bernardomg.darksouls.explorer.item.spell.repository.SpellRepository;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

@Service
public final class DefaultSpellService implements SpellService {

    private final SpellRepository repository;

    @Autowired
    public DefaultSpellService(final SpellRepository repo) {
        super();

        repository = Objects.requireNonNull(repo);
    }

    @Override
    public final PageIterable<? extends SpellSummary>
            getAll(final Pagination pagination, final Sort sort) {
        final Pageable pageable;
        final Page<SpellSummary> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAllSummaries(pageable);

        return Paginations.fromSpring(page);
    }

    @Override
    public final Optional<? extends Spell> getOne(final Long id) {
        final Optional<PersistentSpell> read;
        final Optional<? extends Spell> result;
        final DtoSpell spell;

        read = repository.findById(id);

        if (read.isPresent()) {
            spell = new DtoSpell();

            BeanUtils.copyProperties(read.get(), spell);
            BeanUtils.copyProperties(read.get(), spell.getRequirements());

            result = Optional.of(spell);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
