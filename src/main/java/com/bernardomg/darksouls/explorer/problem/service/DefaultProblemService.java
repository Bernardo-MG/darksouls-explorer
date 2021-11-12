
package com.bernardomg.darksouls.explorer.problem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.query.ProblemsQueries;

@Service
public final class DefaultProblemService implements ProblemService {

    private final ProblemsQueries queries;

    @Autowired
    public DefaultProblemService(final ProblemsQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Page<DataProblem> findProblems(final Pageable page) {
        return queries.findAll(page);
    }

}
