
package com.bernardomg.darksouls.explorer.resolver;

import com.bernardomg.darksouls.explorer.model.Item;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;

public final class NodeDataTypeResolver implements TypeResolver {

    public NodeDataTypeResolver() {
        super();
    }

    @Override
    public GraphQLObjectType getType(final TypeResolutionEnvironment env) {
        final Object javaObject = env.getObject();
        if (javaObject instanceof Item) {
            return env.getSchema().getObjectType("Item");
        } else {
            return env.getSchema().getObjectType("NamedItem");
        }
    }

}
