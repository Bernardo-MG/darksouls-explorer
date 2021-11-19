
package com.bernardomg.darksouls.explorer.problem.initializer;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.query.ProblemsQueries;

@Component
public final class ProblemsInitializer
        implements ApplicationListener<ContextRefreshedEvent> {

    private final ProblemsQueries queries;

    @Autowired
    public ProblemsInitializer(final ProblemsQueries qrs) {
        super();

        queries = Objects.requireNonNull(qrs);
    }

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        Page<DataProblem> data;

        queries.deleteAll();

        data = queries.findDuplicatedItems(Pageable.unpaged());

        queries.save(data);

        data = queries.findItemsWithoutDescription(Pageable.unpaged());

        queries.save(data);
    }

}
