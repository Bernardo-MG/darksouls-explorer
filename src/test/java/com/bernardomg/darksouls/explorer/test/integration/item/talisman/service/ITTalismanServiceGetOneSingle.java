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

package com.bernardomg.darksouls.explorer.test.integration.item.talisman.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.item.talisman.domain.Talisman;
import com.bernardomg.darksouls.explorer.item.talisman.repository.TalismanRepository;
import com.bernardomg.darksouls.explorer.item.talisman.service.TalismanService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;

@IntegrationTest
@DisplayName("Reading single talisman from id")
@Sql({ "/db/queries/talisman/single.sql" })
public class ITTalismanServiceGetOneSingle {

    @Container
    private static final MySQLContainer<?> mysqlContainer = ContainerFactory
        .getMysqlContainer();

    @DynamicPropertySource
    public static void
            setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }

    @Autowired
    private TalismanRepository repository;

    @Autowired
    private TalismanService    service;

    /**
     * Default constructor.
     */
    public ITTalismanServiceGetOneSingle() {
        super();
    }

    @Test
    @DisplayName("Returns the correct damage")
    public void testGetOne_Damage() {
        final Talisman data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(7, data.getDamage()
            .getPhysical());
        Assertions.assertEquals(8, data.getDamage()
            .getMagic());
        Assertions.assertEquals(9, data.getDamage()
            .getFire());
        Assertions.assertEquals(10, data.getDamage()
            .getLightning());
        Assertions.assertEquals(11, data.getDamage()
            .getCritical());
    }

    @Test
    @DisplayName("Returns the correct damage reduction")
    public void testGetOne_DamageReduction() {
        final Talisman data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(12, data.getDamageReduction()
            .getPhysical());
        Assertions.assertEquals(13, data.getDamageReduction()
            .getMagic());
        Assertions.assertEquals(14, data.getDamageReduction()
            .getFire());
        Assertions.assertEquals(15, data.getDamageReduction()
            .getLightning());
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testGetOne_Data() {
        final Talisman data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals("Talisman", data.getName());
        Assertions.assertEquals("Description", data.getDescription());
    }

    @Test
    @DisplayName("Returns no data for a not existing id")
    public void testGetOne_NotExisting() {
        final Optional<? extends Talisman> data;

        data = service.getOne(-1l);

        Assertions.assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Returns the correct requirements")
    public void testGetOne_Requirement() {
        final Talisman data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(3, data.getRequirements()
            .getDexterity());
        Assertions.assertEquals(4, data.getRequirements()
            .getFaith());
        Assertions.assertEquals(5, data.getRequirements()
            .getStrength());
        Assertions.assertEquals(6, data.getRequirements()
            .getIntelligence());
    }

    @Test
    @DisplayName("Returns the correct stats")
    public void testGetOne_Stats() {
        final Talisman data;
        final Long id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(1, data.getDurability());
        Assertions.assertEquals(2, data.getWeight());
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

}
