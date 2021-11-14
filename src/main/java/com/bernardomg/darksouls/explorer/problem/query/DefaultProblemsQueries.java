
package com.bernardomg.darksouls.explorer.problem.query;

import java.util.Map;
import java.util.function.Function;

import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
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
    public final Page<DataProblem> findItemsWithoutDescription(final Pageable page) {
        final BuildableStatement<ResultStatement> noDescStatementBuilder;
        final Function<Map<String, Object>, DataProblem> mapper;

        noDescStatementBuilder = getNoDescriptionItems();

        mapper = (record) -> toProblem("no_description", record);

        return queryExecutor.fetch(noDescStatementBuilder, mapper, page);
    }

    private final BuildableStatement<ResultStatement> getNoDescriptionItems() {
        final Node item;
        final AliasedExpression name;
        final Property description;

        item = Cypher.node("Item").named("i");
        name = item.property("name").as("id");
        description = item.property("description");

        return Cypher.match(item).where(
                description.eq(Cypher.literalOf("")).or(description.isNull()))
                .returning(name);
    }

    private final BuildableStatement<ResultStatement> getDuplicatedItems() {
        final Node item;
        final AliasedExpression name;
        final AliasedExpression count;

        item = Cypher.node("Item").named("i");
        name = item.property("name").as("id");
        count = Functions.count(item).as("count");

        return Cypher.match(item).with(name, count)
                .where(count.gt(Cypher.literalOf(1))).returning(name);
    }

    private final DataProblem toProblem(final String error,
            final Map<String, Object> record) {
        return new DefaultDataProblem((String) record.getOrDefault("id", ""),
                "item", "no_description");
    }

}
