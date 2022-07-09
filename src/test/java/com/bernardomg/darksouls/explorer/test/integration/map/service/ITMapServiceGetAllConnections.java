/**
 * Copyright 2021-2022 the original author or authors
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

package com.bernardomg.darksouls.explorer.test.integration.map.service;

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

import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.map.service.MapService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@IntegrationTest
@ContextConfiguration(initializers = { ITMapServiceGetAllConnections.Initializer.class })
@DisplayName("Reading all the map connections")
public class ITMapServiceGetAllConnections {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
            new Neo4jApplicationContextInitializer(dbContainer).initialize(configurableApplicationContext);
        }
    }

    @Container
    private static final Neo4jContainer<?> dbContainer = ContainerFactory.getNeo4jContainer();

    @BeforeAll
    private static void prepareTestdata() {
        new Neo4jDatabaseInitalizer().initialize("neo4j", dbContainer.getAdminPassword(), dbContainer.getBoltUrl(),
            Arrays.asList("classpath:db/queries/map/connected.cypher"));
    }

    @Autowired
    private MapService service;

    /**
     * Default constructor.
     */
    public ITMapServiceGetAllConnections() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testFindAll_Count() {
        final Iterable<MapConnection> data;

        data = service.getAllConnections(Pagination.disabled(), Sort.disabled());

        Assertions.assertEquals(2, IterableUtils.size(data));
    }

}
