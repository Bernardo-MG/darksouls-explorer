
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
import com.bernardomg.darksouls.explorer.problem.query.ProblemsQueries;

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
    public final Page<DataProblem> getAll(final Pageable page) {
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
        final Collection<DataProblem> itemNoDescription;
        final Collection<DataProblem> itemsDuplicated;
        final Collection<DataProblem> actorsDuplicated;
        final Collection<DataProblem> itemsWithoutSource;

        queries.deleteAll();

        itemNoDescription = queries.findMissingField("Item", "description");
        itemsDuplicated = queries.findDuplicated("Item");
        actorsDuplicated = queries.findDuplicated("Actor");
        itemsWithoutSource = queries.findMissingRelationships("Item",
            Arrays.asList("DROPS", "SELLS", "STARTS_WITH", "REWARDS",
                "CHOSEN_FROM", "ASCENDS", "LOOT", "CHOSEN_FROM"));

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
