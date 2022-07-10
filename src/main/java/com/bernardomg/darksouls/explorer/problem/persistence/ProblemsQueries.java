
package com.bernardomg.darksouls.explorer.problem.persistence;

import java.util.Collection;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;

public interface ProblemsQueries {

    public Collection<? extends DataProblem> findDuplicated(final String node);

    public Collection<? extends DataProblem> findMissingField(final String node, final String field);

    public Collection<? extends DataProblem> findMissingRelationships(final String node,
            final Iterable<String> relationships);

}
