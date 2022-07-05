
package com.bernardomg.darksouls.explorer.problem.service;

import org.springframework.data.domain.Page;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface ProblemService {

    public Page<? extends DataProblem> getAll(final Pagination pagination, final Sort sort);

    public void recollectAndRegister();

}
