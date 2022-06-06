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

package com.bernardomg.darksouls.explorer.test.integration.item.shield.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.item.shield.domain.Spell;
import com.bernardomg.darksouls.explorer.item.shield.service.ShieldService;
import com.bernardomg.darksouls.explorer.item.shield.shield.ShieldRepository;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;

@IntegrationTest
@ContextConfiguration(
        initializers = { ITShieldServiceGetOneSingle.Initializer.class })
@DisplayName("Reading single shield from id")
@Sql({ "/db/queries/shield/single.sql" })
public class ITShieldServiceGetOneSingle {

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(
                final ConfigurableApplicationContext configurableApplicationContext) {
            new Neo4jApplicationContextInitializer(neo4jContainer)
                .initialize(configurableApplicationContext);
        }
    }

    @Container
    private static final MySQLContainer<?> mysqlContainer = ContainerFactory
        .getMysqlContainer();

    @Container
    private static final Neo4jContainer<?> neo4jContainer = ContainerFactory
        .getNeo4jContainer();

    @DynamicPropertySource
    public static void
            setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }

    @Autowired
    private ShieldRepository repository;

    @Autowired
    private ShieldService    service;

    /**
     * Default constructor.
     */
    public ITShieldServiceGetOneSingle() {
        super();
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testGetOne_Data() {
        final Spell data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals("Shield", data.getName());
        Assertions.assertEquals("Description", data.getDescription());
    }

    @Test
    @DisplayName("Returns no data for a not existing id")
    public void testGetOne_NotExisting() {
        final Optional<? extends Spell> data;

        data = service.getOne(-1l);

        Assertions.assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Returns the correct requirements")
    public void testGetOne_Requirement() {
        final Spell data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(0, data.getDexterity());
        Assertions.assertEquals(1, data.getFaith());
        Assertions.assertEquals(2, data.getStrength());
        Assertions.assertEquals(3, data.getIntelligence());
    }

    @Test
    @DisplayName("Returns the correct requirements")
    public void testGetOne_Stats() {
        final Spell data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(4, data.getDurability());
        Assertions.assertEquals(5, data.getWeight());
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

}
