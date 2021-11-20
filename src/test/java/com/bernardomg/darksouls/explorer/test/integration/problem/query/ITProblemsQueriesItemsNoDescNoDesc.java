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

package com.bernardomg.darksouls.explorer.test.integration.problem.query;

import java.util.Arrays;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.query.ProblemsQueries;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(
        initializers = { ITProblemsQueriesItemsNoDescNoDesc.Initializer.class })
@DisplayName("Query for items without description using item with no description")
public class ITProblemsQueriesItemsNoDescNoDesc {

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
                Arrays.asList(
                        "classpath:db/queries/item/no_description.cypher"));
    }

    @Autowired
    private ProblemsQueries queries;

    /**
     * Default constructor.
     */
    public ITProblemsQueriesItemsNoDescNoDesc() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testFindAll_Count() {
        final Iterable<DataProblem> data;

        data = queries.findItemsWithoutDescription();

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testFindAll_Data() {
        final DataProblem data;

        data = queries.findItemsWithoutDescription().iterator().next();

        Assertions.assertEquals("Item", data.getId());
        Assertions.assertEquals("item", data.getSource());
        Assertions.assertEquals("no_description", data.getProblem());
    }

}
