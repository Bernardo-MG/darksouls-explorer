
package com.bernardomg.darksouls.explorer.search.query;

import java.util.Map;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingWithoutWhere;

import com.bernardomg.darksouls.explorer.search.domain.ImmutableSearchResult;
import com.bernardomg.darksouls.explorer.search.domain.SearchResult;
import com.bernardomg.persistence.executor.Query;

public final class SearchQuery implements Query<SearchResult> {

    private final String name;

    public SearchQuery(final String name) {
        super();

        this.name = name;
    }

    @Override
    public final SearchResult getOutput(final Map<String, Object> record) {
        final Long   id;
        final String name;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");

        return new ImmutableSearchResult(id, name, "/items");
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final Node                       item;
        final Expression                 nodeName;
        final OngoingReadingWithoutWhere ongoingBuilder;
        final Condition                  nameCondition;

        item = Cypher.node("Item")
            .named("i");
        nodeName = item.property("name");

        ongoingBuilder = Cypher.match(item);

        if (!name.isBlank()) {
            nameCondition = nodeName.matches("(?i).*" + name + ".*");
            ongoingBuilder.where(nameCondition);
        }

        return ongoingBuilder.returning(Functions.id(item)
            .as("id"), nodeName.as("name"))
            .build()
            .getCypher();
    }

}
