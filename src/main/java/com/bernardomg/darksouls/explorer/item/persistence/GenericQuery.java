
package com.bernardomg.darksouls.explorer.item.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingWithoutWhere;

import com.bernardomg.darksouls.explorer.persistence.model.Query;

public abstract class GenericQuery<T> implements Query<T> {

    private final Collection<String> fields;

    private final String             label;

    public GenericQuery(final String lbl, final Collection<String> flds) {
        super();

        label = lbl;
        fields = flds;
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final Node root;
        final Expression nodeName;
        final Expression nodeId;
        final OngoingReadingWithoutWhere builder;
        final Condition nameCondition;
        final Condition idCondition;
        final String name;
        final Collection<Expression> expressions;

        // Root
        root = Cypher.node(label)
            .named("s");
        nodeName = root.property("name");
        nodeId = Functions.id(root);

        builder = Cypher.match(root);

        if (params.containsKey("name")) {
            name = String.valueOf(params.get("name"));
            nameCondition = nodeName.matches("(?i).*" + name + ".*");
            builder.where(nameCondition);
        }

        if (params.containsKey("id")) {
            idCondition = nodeId.eq(Cypher.parameter("id"));
            // TODO: combine
            builder.where(idCondition);
        }

        expressions = new ArrayList<>();
        expressions.add(nodeId.as("id"));
        expressions.add(nodeName.as("name"));
        expressions.addAll(fields.stream()
            .filter((n) -> !n.equals("id"))
            .filter((n) -> !n.equals("name"))
            .map((f) -> root.property(f)
                .as(f))
            .collect(Collectors.toList()));

        return builder.returning(expressions)
            .build()
            .getCypher();
    }

}
