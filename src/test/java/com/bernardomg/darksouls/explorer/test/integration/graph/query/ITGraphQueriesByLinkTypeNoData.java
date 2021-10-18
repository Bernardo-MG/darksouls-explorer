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
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(
        initializers = { ITGraphQueriesByLinkTypeNoData.Initializer.class })
@DisplayName("Querying the repository filtering by type with no data")
public class ITGraphQueriesByLinkTypeNoData {

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

    @Autowired
    private GraphQueries                   queries;

    /**
     * Default constructor.
     */
    public ITGraphQueriesByLinkTypeNoData() {
        super();
    }

    @Test
    @DisplayName("Returns no data")
    public void testFindAllByLinkType_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP", "ABC"));

        Assertions.assertEquals(0, Iterables.size(data.getLinks()));
        Assertions.assertEquals(0, Iterables.size(data.getNodes()));
        Assertions.assertEquals(0, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns no data for an empty type list")
    public void testFindAllByLinkType_Empty_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Collections.emptyList());

        Assertions.assertEquals(0, Iterables.size(data.getLinks()));
        Assertions.assertEquals(0, Iterables.size(data.getNodes()));
        Assertions.assertEquals(0, Iterables.size(data.getTypes()));
    }

}
