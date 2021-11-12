
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.Map;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Property;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.renderer.Renderer;
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

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultProblemsQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt,
                Renderer.getDefaultRenderer());
    }

    @Override
    public final Page<DataProblem> findAll(final Pageable page) {
        final Node item;
        final AliasedExpression name;
        final Property description;
        final BuildableStatement<ResultStatement> statementBuilder;

        item = Cypher.node("Item").named("i");
        name = item.property("name").as("id");
        description = item.property("description");

        statementBuilder = Cypher.match(item).where(
                description.eq(Cypher.literalOf("")).or(description.isNull()))
                .returning(name);

        return queryExecutor.fetch(statementBuilder, this::toProblem, page);
    }

    private final DataProblem toProblem(final Map<String, Object> record) {
        return new DefaultDataProblem((String) record.getOrDefault("id", ""),
                "item", "no_description");
    }

}
