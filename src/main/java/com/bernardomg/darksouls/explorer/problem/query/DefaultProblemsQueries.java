
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.model.DefaultDataProblem;

@Component
public final class DefaultProblemsQueries implements ProblemsQueries {

    private final Neo4jClient      client;

    private final QueryExecutor    queryExecutor;

    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public DefaultProblemsQueries(final DataSource dataSource,
            final Neo4jClient clnt) {
        super();

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("problems")
            .usingGeneratedKeyColumns("id");
        client = Objects.requireNonNull(clnt);
        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final void deleteAll() {
        client.query("MATCH (p:Problem) DELETE p")
            .run();
    }

    @Override
    public final Page<DataProblem> findAll(final Pageable page) {
        final Function<Map<String, Object>, DataProblem> mapper;

        mapper = this::toProblem;

        return queryExecutor.fetch(
        // @formatter:off
              "MATCH"
            + "  (p:Problem)"
            + "RETURN"
            + "  p.id AS id, p.source AS source, p.problem AS problem",
            // @formatter:on
            mapper, page);
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
    public final void save(final Iterable<DataProblem> data) {
        Map<String, Object> parameters;

        for (final DataProblem dataProblem : data) {
            parameters = new HashMap<String, Object>();
            parameters.put("problem", dataProblem.getProblem());
            parameters.put("source", dataProblem.getSource());

            simpleJdbcInsert.execute(parameters);
        }
    }

    private final DataProblem toProblem(final Map<String, Object> record) {
        return new DefaultDataProblem((String) record.getOrDefault("id", ""),
            (String) record.getOrDefault("source", ""),
            (String) record.getOrDefault("problem", ""));
    }

    private final DataProblem toProblem(final String error, final String source,
            final Map<String, Object> record) {
        return new DefaultDataProblem((String) record.getOrDefault("id", ""),
            source, error);
    }

}
