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

package com.bernardomg.darksouls.explorer.test.integration.item.service;

import java.util.Arrays;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.service.ItemService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(
        initializers = { ITItemServiceFindAllPaged.Initializer.class })
@DisplayName("Reading all the items")
public class ITItemServiceFindAllPaged {

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
                Arrays.asList("classpath:db/queries/item/multiple.cypher"));
    }

    @Autowired
    private ItemService service;

    /**
     * Default constructor.
     */
    public ITItemServiceFindAllPaged() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testFindAll_Instance() {
        final Iterable<Item> data;

        data = service.getAll(Pageable.ofSize(1));

        Assertions.assertInstanceOf(Page.class, data);
    }

    @Test
    @DisplayName("Applies pagination size")
    public void testFindAll_SingleResult() {
        final Iterable<Item> data;

        data = service.getAll(Pageable.ofSize(1));

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    @Test
    @DisplayName("When unpaged returns all the data")
    public void testFindAll_Unpaged() {
        final Iterable<Item> data;

        data = service.getAll(Pageable.unpaged());

        Assertions.assertEquals(5, IterableUtils.size(data));
    }

}
