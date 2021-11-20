
package com.bernardomg.darksouls.explorer.problem.initializer;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.query.ProblemsQueries;

@Component
public final class ProblemsInitializer
        implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Logger.
     */
    private static final Logger   LOGGER = LoggerFactory
            .getLogger(ProblemsInitializer.class);

    private final ProblemsQueries queries;

    @Autowired
    public ProblemsInitializer(final ProblemsQueries qrs) {
        super();

        queries = Objects.requireNonNull(qrs);
    }

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        Iterable<DataProblem> data;

        queries.deleteAll();

        data = queries.findDuplicatedItems();

        LOGGER.debug("Duplicated items: {}", data);

        queries.save(data);

        data = queries.findItemsWithoutDescription();

        LOGGER.debug("Items without description: {}", data);

        queries.save(data);
    }

}
