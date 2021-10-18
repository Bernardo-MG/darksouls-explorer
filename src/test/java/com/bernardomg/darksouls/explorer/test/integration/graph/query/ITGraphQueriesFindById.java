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

package com.bernardomg.darksouls.explorer.test.integration.graph.query;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.Application;
import com.bernardomg.darksouls.explorer.graph.model.Info;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(
        initializers = { ITGraphQueriesFindById.Initializer.class })
@SpringBootTest(classes = Application.class)
@DisplayName("Querying the repository by id")
public class ITGraphQueriesFindById {

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(
                final ConfigurableApplicationContext configurableApplicationContext) {
            new Neo4jApplicationContextInitializer(dbContainer)
                    .initialize(configurableApplicationContext);
        }
    }

    @Container
    private static final Neo4jContainer<?> dbContainer = ContainerFactory
            .getNeo4jContainer();

    @BeforeAll
    private static void prepareTestdata() {
        new Neo4jDatabaseInitalizer().initialize("neo4j",
                dbContainer.getAdminPassword(), dbContainer.getBoltUrl(),
                Arrays.asList("classpath:db/queries/graph/simple.cypher"));
    }

    @Autowired
    private GraphQueries queries;

    /**
     * Default constructor.
     */
    public ITGraphQueriesFindById() {
        super();
    }

    @Test
    @DisplayName("Returns no data for a not existing id")
    public void testFindById_NotExisting() {
        final Optional<Info> data;

        data = queries.findById(12345l);

        Assertions.assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testFindById_Single_Data() {
        final Optional<Info> data;
        final Long id;

        id = queries.findAll().getNodes().iterator().next().getId();
        data = queries.findById(id);

        Assertions.assertNotNull(data.get().getId());
        Assertions.assertEquals("Target", data.get().getName());
        Assertions.assertEquals(0, Iterables.size(data.get().getDescription()));
    }

}
