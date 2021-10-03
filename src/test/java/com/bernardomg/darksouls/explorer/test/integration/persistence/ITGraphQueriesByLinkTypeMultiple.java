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

package com.bernardomg.darksouls.explorer.test.integration.persistence;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.bernardomg.darksouls.explorer.Application;
import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@Testcontainers
@ContextConfiguration(
        initializers = { ITGraphQueriesByLinkTypeMultiple.Initializer.class })
@SpringBootTest(classes = Application.class)
@DisplayName("Querying the repository filtering by type with multiple data")
public class ITGraphQueriesByLinkTypeMultiple {

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(
                final ConfigurableApplicationContext configurableApplicationContext) {

            neo4jContainer.addExposedPorts(7687);
            TestPropertyValues
                    .of("spring.neo4j.uri=" + neo4jContainer.getBoltUrl(),
                            "spring.neo4j.authentication.username=neo4j",
                            "spring.neo4j.authentication.password="
                                    + neo4jContainer.getAdminPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Container
    private static final Neo4jContainer<?> neo4jContainer = ContainerFactory
            .getNeo4jContainer();

    @BeforeAll
    private static void prepareTestdata() {
        new Neo4jDatabaseInitalizer().initialize("neo4j",
                neo4jContainer.getAdminPassword(), neo4jContainer.getBoltUrl(),
                Arrays.asList("classpath:db/queries/graph/multiple.cypher"));
    }

    @Autowired
    private GraphQueries queries;

    /**
     * Default constructor.
     */
    public ITGraphQueriesByLinkTypeMultiple() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testFindAllByLinkType_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP"));

        Assertions.assertEquals(2, Iterables.size(data.getLinks()));
        Assertions.assertEquals(3, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

}
