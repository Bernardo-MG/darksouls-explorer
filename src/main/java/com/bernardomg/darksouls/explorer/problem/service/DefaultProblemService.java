
package com.bernardomg.darksouls.explorer.problem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.model.PersistentDataProblem;
import com.bernardomg.darksouls.explorer.problem.persistence.DataProblemRepository;
import com.bernardomg.darksouls.explorer.problem.persistence.ProblemsQueries;

@Service
public final class DefaultProblemService implements ProblemService {

    /**
     * Logger.
     */
    private static final Logger         LOGGER = LoggerFactory
        .getLogger(DefaultProblemService.class);

    private final ProblemsQueries       queries;

    private final DataProblemRepository repository;

    @Autowired
    public DefaultProblemService(final DataProblemRepository repo,
            final ProblemsQueries queries) {
        super();

        repository = Objects.requireNonNull(repo);
        this.queries = queries;
    }

    @Override
    public final Page<? extends DataProblem> getAll(final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;

        pageable = Paginations.toSpring(pagination, sort);

        return repository.findAll(pageable);
    }

    @Override
    public final void recollectAndRegister() {
        Iterable<DataProblem> data;

        data = recollect();

        save(data);
    }

    private final Iterable<DataProblem> recollect() {
        final Collection<DataProblem> data;
        final Collection<? extends DataProblem> itemNoDescription;
        final Collection<? extends DataProblem> itemsDuplicated;
        final Collection<? extends DataProblem> actorsDuplicated;
        final Collection<? extends DataProblem> itemsWithoutSource;

        repository.deleteAll();

        itemNoDescription = queries.findMissingField("Item", "description");
        itemsDuplicated = queries.findDuplicated("Item");
        actorsDuplicated = queries.findDuplicated("Actor");
        itemsWithoutSource = queries.findMissingRelationships("Item",
            Arrays.asList("DROPS", "SELLS", "STARTS_WITH", "REWARDS",
                "CHOSEN_FROM", "ASCENDS", "LOOT", "DROPS_IN_COMBAT"));
        // TODO: Items with no location

        LOGGER.trace("Duplicated items: {}", itemsDuplicated);
        LOGGER.trace("Items without description: {}", itemNoDescription);
        LOGGER.trace("Actors without description: {}", actorsDuplicated);
        LOGGER.trace("Items without source: {}", itemsWithoutSource);

        data = new ArrayList<>();
        data.addAll(itemsDuplicated);
        data.addAll(itemNoDescription);
        data.addAll(actorsDuplicated);
        data.addAll(itemsWithoutSource);

        return data;
    }

    private final void save(final Iterable<? extends DataProblem> data) {
        final Collection<PersistentDataProblem> entities;
        PersistentDataProblem entity;

        entities = new ArrayList<>();
        for (final DataProblem dataProblem : data) {
            entity = new PersistentDataProblem();
            entity.setName(dataProblem.getName());
            entity.setProblem(dataProblem.getProblem());
            entity.setSource(dataProblem.getSource());

            entities.add(entity);
        }

        repository.saveAll(entities);
    }

}
