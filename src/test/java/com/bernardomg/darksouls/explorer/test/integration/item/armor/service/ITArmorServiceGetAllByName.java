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

package com.bernardomg.darksouls.explorer.test.integration.item.armor.service;

import org.apache.commons.collections4.IterableUtils;
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

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.request.DefaultArmorRequest;
import com.bernardomg.darksouls.explorer.item.armor.service.ArmorService;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledPagination;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledSort;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;

@IntegrationTest
@ContextConfiguration(
        initializers = { ITArmorServiceGetAllByName.Initializer.class })
@DisplayName("Reading all the armors by name")
@Sql({ "/db/queries/armor/single.sql" })
public class ITArmorServiceGetAllByName {

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
    private ArmorService service;

    /**
     * Default constructor.
     */
    public ITArmorServiceGetAllByName() {
        super();
    }

    @Test
    @DisplayName("Returns all the data when searching by full name")
    public void testGetByName_FullName_Count() {
        final Iterable<? extends Armor> data;
        final DefaultArmorRequest request;

        request = new DefaultArmorRequest();
        request.setName("Armor name");

        data = service.getAll(request, new DisabledPagination(),
            new DisabledSort());

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Returns the correct data when searching by full name")
    public void testGetByName_FullName_Data() {
        final Armor data;
        final DefaultArmorRequest request;

        request = new DefaultArmorRequest();
        request.setName("Armor name");

        data = service
            .getAll(request, new DisabledPagination(), new DisabledSort())
            .iterator()
            .next();

        Assertions.assertEquals("Armor name", data.getName());
        Assertions.assertEquals("Description", data.getDescription());
    }

    @Test
    @DisplayName("Returns all the data when searching by partial name")
    public void testGetByName_PartialName_Count() {
        final Iterable<? extends Armor> data;
        final DefaultArmorRequest request;

        request = new DefaultArmorRequest();
        request.setName("name");

        data = service.getAll(request, new DisabledPagination(),
            new DisabledSort());

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

}
