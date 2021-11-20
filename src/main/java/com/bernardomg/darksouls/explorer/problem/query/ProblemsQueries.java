
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;

public interface ProblemsQueries {

    public void deleteAll();

    public Page<DataProblem> findAll(final Pageable page);

    public Collection<DataProblem> findDuplicated(final String node);

    public Collection<DataProblem> findMissingField(final String node,
            final String field);

    public void save(final Iterable<DataProblem> data);

}
