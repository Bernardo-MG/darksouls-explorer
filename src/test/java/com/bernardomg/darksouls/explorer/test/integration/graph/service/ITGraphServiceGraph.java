/**
 * Copyright 2021 the original author or authors
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

package com.bernardomg.darksouls.explorer.test.integration.graph.service;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.graph.service.GraphService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(initializers = { ITGraphServiceGraph.Initializer.class })
@DisplayName("Getting the graph from the service")
public class ITGraphServiceGraph {

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(
                final ConfigurableApplicationContext configurableApplicationContext) {

            dbContainer.addExposedPorts(7687);
            TestPropertyValues
                .of("spring.neo4j.uri=" + dbContainer.getBoltUrl(),
                    "spring.neo4j.authentication.username=neo4j",
                    "spring.neo4j.authentication.password="
                            + dbContainer.getAdminPassword())
                .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Container
    private static final Neo4jContainer<?> dbContainer = ContainerFactory
        .getNeo4jContainer();

    @BeforeAll
    private static void prepareTestdata() {
        new Neo4jDatabaseInitalizer().initialize("neo4j",
            dbContainer.getAdminPassword(), dbContainer.getBoltUrl(),
            Arrays.asList(
                "classpath:db/queries/graph/multiple_relationships.cypher"));
    }

    @Autowired
    private GraphService service;

    /**
     * Default constructor.
     */
    public ITGraphServiceGraph() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testGetGraph_Count() {
        final Graph data;

        data = service.getGraph(Collections.emptyList());

        Assertions.assertEquals(2, IterableUtils.size(data.getLinks()));
        Assertions.assertEquals(2, IterableUtils.size(data.getNodes()));
        Assertions.assertEquals(2, IterableUtils.size(data.getTypes()));
    }

    @Test
    @DisplayName("Filters by relationship")
    public void testGetGraph_Filter_Count() {
        final Graph data;

        data = service.getGraph(Arrays.asList("RELATIONSHIP"));

        Assertions.assertEquals(1, IterableUtils.size(data.getLinks()));
        Assertions.assertEquals(2, IterableUtils.size(data.getNodes()));
        Assertions.assertEquals(1, IterableUtils.size(data.getTypes()));
    }

}
