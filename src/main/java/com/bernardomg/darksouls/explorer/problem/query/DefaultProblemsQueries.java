
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.model.DefaultDataProblem;

@Component
public final class DefaultProblemsQueries implements ProblemsQueries {

    private final Neo4jClient   client;

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultProblemsQueries(final Neo4jClient clnt) {
        super();

        client = Objects.requireNonNull(clnt);
        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final void deleteAll() {
        client.query("MATCH (p:Problem) DELETE p").run();
    }

    @Override
    public final Page<DataProblem> findAll(final Pageable page) {
        final Function<Map<String, Object>, DataProblem> mapper;

        mapper = this::toProblem;

        return queryExecutor.fetch(
                "MATCH (p:Problem) RETURN p.id AS id, p.source AS source, p.problem AS problem",
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
    public final void save(final Iterable<DataProblem> data) {
        final Node p;
        final Collection<Statement> statements;
        Statement statement;

        p = Cypher.node("Problem").named("p");

        statements = new ArrayList<>();

        for (final DataProblem dataProblem : data) {
            statement = Cypher.merge(p.withProperties(Cypher.mapOf("id",
                    Cypher.literalOf(dataProblem.getId()), "problem",
                    Cypher.literalOf(dataProblem.getProblem()), "source",
                    Cypher.literalOf(dataProblem.getSource())))).build();

            statements.add(statement);
        }

        queryExecutor.run(statements);
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
