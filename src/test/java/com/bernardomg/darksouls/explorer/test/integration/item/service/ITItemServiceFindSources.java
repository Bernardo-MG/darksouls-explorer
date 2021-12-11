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
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.service.ItemService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

@IntegrationTest
@ContextConfiguration(
        initializers = { ITItemServiceFindSources.Initializer.class })
@DisplayName("Reading item sources")
public class ITItemServiceFindSources {

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
                "classpath:db/queries/item/with_merchant_source.cypher"));
    }

    @Autowired
    private Neo4jClient client;

    @Autowired
    private ItemService service;

    /**
     * Default constructor.
     */
    public ITItemServiceFindSources() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for an id")
    public void testGetSources_Count() {
        final Iterable<ItemSource> data;
        final Long id;

        id = getId();

        data = service.getSources(id, Pageable.unpaged());

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Returns the correct data for an id")
    public void testGetSources_Data() {
        final ItemSource data;
        final Long id;

        id = getId();

        data = service.getSources(id, Pageable.unpaged())
            .iterator()
            .next();

        Assertions.assertEquals("Item name", data.getItem());
        Assertions.assertEquals("Merchant", data.getSource());
        Assertions.assertEquals("sold", data.getRelationship());
        Assertions.assertEquals("Location", data.getLocation());
    }

    private final Long getId() {
        final Collection<Map<String, Object>> rows;

        rows = client.query("MATCH (n) RETURN n")
            .fetch()
            .all();

        return (Long) rows.stream()
            .findFirst()
            .get()
            .getOrDefault("id", 0l);
    }

}
