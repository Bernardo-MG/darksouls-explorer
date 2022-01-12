
package com.bernardomg.darksouls.explorer.problem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.persistence.ProblemsQueries;

@Service
public final class DefaultProblemService implements ProblemService {

    /**
     * Logger.
     */
    private static final Logger   LOGGER = LoggerFactory
        .getLogger(DefaultProblemService.class);

    private final ProblemsQueries queries;

    @Autowired
    public DefaultProblemService(final ProblemsQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Page<? extends DataProblem> getAll(final Pageable page) {
        return queries.findAll(page);
    }

    @Override
    public final void recollectAndRegister() {
        Iterable<DataProblem> data;

        data = recollect();

        queries.save(data);
    }

    private final Iterable<DataProblem> recollect() {
        final Collection<DataProblem> data;
        final Collection<? extends DataProblem> itemNoDescription;
        final Collection<? extends DataProblem> itemsDuplicated;
        final Collection<? extends DataProblem> actorsDuplicated;
        final Collection<? extends DataProblem> itemsWithoutSource;

        queries.deleteAll();

        itemNoDescription = queries.findMissingField("Item", "description");
        itemsDuplicated = queries.findDuplicated("Item");
        actorsDuplicated = queries.findDuplicated("Actor");
        itemsWithoutSource = queries.findMissingRelationships("Item",
            Arrays.asList("DROPS", "SELLS", "STARTS_WITH", "REWARDS",
                "CHOSEN_FROM", "ASCENDS", "LOOT", "DROPS_IN_COMBAT"));
        // TODO: Items with no location

        LOGGER.debug("Duplicated items: {}", itemsDuplicated);
        LOGGER.debug("Items without description: {}", itemNoDescription);
        LOGGER.debug("Actors without description: {}", actorsDuplicated);
        LOGGER.debug("Items without source: {}", itemsWithoutSource);

        data = new ArrayList<>();
        data.addAll(itemsDuplicated);
        data.addAll(itemNoDescription);
        data.addAll(actorsDuplicated);
        data.addAll(itemsWithoutSource);

        return data;
    }

}
