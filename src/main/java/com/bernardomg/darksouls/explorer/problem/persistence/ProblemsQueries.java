
package com.bernardomg.darksouls.explorer.problem.persistence;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;

public interface ProblemsQueries {

    public void deleteAll();

    public Page<? extends DataProblem> findAll(final Pageable page);

    public Collection<? extends DataProblem> findDuplicated(final String node);

    public Collection<? extends DataProblem> findMissingField(final String node,
            final String field);

    public Collection<? extends DataProblem> findMissingRelationships(
            final String node, final Iterable<String> relationships);

    public void save(final Iterable<? extends DataProblem> data);

}
