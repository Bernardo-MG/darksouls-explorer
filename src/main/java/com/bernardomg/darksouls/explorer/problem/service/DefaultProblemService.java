
package com.bernardomg.darksouls.explorer.problem.service;

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
        Iterable<DataProblem> data;

        queries.deleteAll();

        data = queries.findDuplicatedItems();

        LOGGER.debug("Duplicated items: {}", data);

        queries.save(data);

        data = queries.findItemsWithoutDescription();

        LOGGER.debug("Items without description: {}", data);

        return data;
    }

}
