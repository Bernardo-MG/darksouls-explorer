
package com.bernardomg.darksouls.explorer.problem.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.query.DuplicatedProblemQuery;
import com.bernardomg.darksouls.explorer.problem.query.MissingFieldQuery;
import com.bernardomg.darksouls.explorer.problem.query.MissingRelationshipQuery;
import com.bernardomg.pagination.model.Query;
import com.bernardomg.persistence.executor.QueryExecutor;
import com.bernardomg.persistence.executor.TextQueryExecutor;

@Component
public final class DefaultProblemsQueries implements ProblemsQueries {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultProblemsQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new TextQueryExecutor(clnt);
    }

    @Override
    public final Collection<DataProblem> findDuplicated(final String node) {
        final Map<String, Object> params;
        final Query<DataProblem> query;

        params = new HashMap<>();
        params.put("node", node);

        query = new DuplicatedProblemQuery(node);

        return queryExecutor.fetch(query::getStatement, query::getOutput,
            params);
    }

    @Override
    public final Collection<DataProblem> findMissingField(final String node,
            final String field) {
        final Map<String, Object> params;
        final Query<DataProblem> query;

        params = new HashMap<>();
        params.put("node", node);

        query = new MissingFieldQuery(node, field);

        return queryExecutor.fetch(query::getStatement, query::getOutput,
            params);
    }

    @Override
    public final Collection<DataProblem> findMissingRelationships(
            final String node, final Iterable<String> relationships) {
        final String mergedRels;
        final Map<String, Object> params;
        final Query<DataProblem> query;

        mergedRels = String.join("|", relationships);

        params = new HashMap<>();
        params.put("node", node);
        params.put("relationships", mergedRels);

        query = new MissingRelationshipQuery(node, relationships);

        return queryExecutor.fetch(query::getStatement, query::getOutput,
            params);
    }

}
