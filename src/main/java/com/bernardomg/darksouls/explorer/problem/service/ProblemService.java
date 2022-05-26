
package com.bernardomg.darksouls.explorer.problem.service;

import org.springframework.data.domain.Page;

import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;

public interface ProblemService {

    public Page<? extends DataProblem> getAll(final Pagination pagination,
            final Sort sort);

    public void recollectAndRegister();

}
