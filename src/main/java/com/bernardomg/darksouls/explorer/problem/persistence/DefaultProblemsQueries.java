
package com.bernardomg.darksouls.explorer.problem.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.TextQueryExecutor;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.model.ImmutableDataProblem;
import com.bernardomg.darksouls.explorer.problem.model.PersistentDataProblem;

@Component
public final class DefaultProblemsQueries implements ProblemsQueries {

    private final QueryExecutor         queryExecutor;

    private final DataProblemRepository repository;

    @Autowired
    public DefaultProblemsQueries(final DataProblemRepository repo,
            final Neo4jClient clnt) {
        super();

        repository = Objects.requireNonNull(repo);
        queryExecutor = new TextQueryExecutor(clnt);
    }

    @Override
    public final void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public final Page<? extends DataProblem> findAll(final Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public final Collection<DataProblem> findDuplicated(final String node) {
        final Function<Map<String, Object>, DataProblem> mapper;
        final String template;
        final String query;

        mapper = (record) -> toProblem("duplicated", node, record);

        // TODO: Use query parameters
        template = "MATCH (n:%s) WITH n.name AS id, count(n) AS count WHERE count > 1 RETURN id";
        query = String.format(template, node);

        return queryExecutor.fetch(query, mapper);
    }

    @Override
    public final Collection<DataProblem> findMissingField(final String node,
            final String field) {
        final Function<Map<String, Object>, DataProblem> mapper;
        final String template;
        final String query;

        mapper = (record) -> toProblem("no_description", node, record);

        // TODO: Use query parameters
        template = "MATCH (n:%1$s) WHERE (n.%2$s = '' OR n.%2$s IS NULL) RETURN n.name AS id";
        query = String.format(template, node, field);

        return queryExecutor.fetch(query, mapper);
    }

    @Override
    public final Collection<DataProblem> findMissingRelationships(
            final String node, final Iterable<String> relationships) {
        final Function<Map<String, Object>, DataProblem> mapper;
        final String mergedRels;
        final Map<String, Object> params;
        final String template;
        final String query;

        mapper = (record) -> toProblem("no_relationships", node, record);

        mergedRels = String.join("|", relationships);

        params = new HashMap<>();
        params.put("node", node);
        params.put("relationships", mergedRels);

        // TODO: Use query parameters
        // @formatter:off
        template =
              "MATCH" + System.lineSeparator()
            + "  (n:%s)" + System.lineSeparator()
            + "WHERE" + System.lineSeparator()
            + "  NOT ()-[:%s]->(n)" + System.lineSeparator()
            + "RETURN" + System.lineSeparator()
            + "  n.name AS id";
        // @formatter:on
        query = String.format(template, node, mergedRels);

        return queryExecutor.fetch(query, mapper);
    }

    @Override
    public final void save(final Iterable<? extends DataProblem> data) {
        final Collection<PersistentDataProblem> entities;
        PersistentDataProblem entity;

        entities = new ArrayList<>();
        for (final DataProblem dataProblem : data) {
            entity = new PersistentDataProblem();
            entity.setName(dataProblem.getName());
            entity.setProblem(dataProblem.getProblem());
            entity.setSource(dataProblem.getSource());

            entities.add(entity);
        }

        repository.saveAll(entities);
    }

    private final DataProblem toProblem(final String error, final String source,
            final Map<String, Object> record) {
        return new ImmutableDataProblem((String) record.getOrDefault("id", ""),
            source, error);
    }

}
