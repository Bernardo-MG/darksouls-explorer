/**
 * Copyright 2020 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.darksouls.explorer.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.graphql.GraphDataFetcher;
import com.bernardomg.darksouls.explorer.graphql.ItemDataFetcher;
import com.bernardomg.darksouls.explorer.graphql.NodeDataFetcher;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

/**
 * GraphQL configuration.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class GraphqlConfig {

    /**
     * GraphQL definitions.
     */
    @Value("classpath:graphql/darksouls.graphqls")
    private Resource         definitions;

    @Autowired
    private GraphDataFetcher graphDataFetcher;

    @Autowired
    private NodeDataFetcher  nodeDataFetcher;

    @Autowired
    private ItemDataFetcher  itemDataFetcher;

    public GraphqlConfig() {
        super();
    }

    @Bean
    public GraphQL graphQL() throws IOException {
        final File file;
        final TypeDefinitionRegistry typeDefinitionRegistry;
        final RuntimeWiring runtimeWiring;
        final GraphQLSchema graphQLSchema;

        file = definitions.getFile();
        typeDefinitionRegistry = new SchemaParser().parse(file);
        runtimeWiring = buildRuntimeWiring();
        graphQLSchema = new SchemaGenerator()
                .makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring().type("Query",
                typeWiring -> typeWiring.dataFetcher("graph", graphDataFetcher))
                .type("Query",
                        typeWiring -> typeWiring.dataFetcher("info",
                                nodeDataFetcher))
                .type("Query", typeWiring -> typeWiring.dataFetcher("item",
                        itemDataFetcher))
                .build();
    }

}
