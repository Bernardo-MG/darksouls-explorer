
package com.bernardomg.darksouls.explorer.problem.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;

public interface ProblemsQueries {

    public Page<DataProblem> findDuplicatedItems(final Pageable page);

    public Page<DataProblem> findItemsWithoutDescription(final Pageable page);

}
