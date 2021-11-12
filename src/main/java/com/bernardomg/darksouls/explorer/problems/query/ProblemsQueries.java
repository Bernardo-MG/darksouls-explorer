
package com.bernardomg.darksouls.explorer.problems.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.problems.model.DataProblem;

public interface ProblemsQueries {

    public Page<DataProblem> findAll(final Pageable page);

}
