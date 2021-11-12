
package com.bernardomg.darksouls.explorer.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;

public interface ProblemService {

    public Page<DataProblem> findProblems(final Pageable page);

}
