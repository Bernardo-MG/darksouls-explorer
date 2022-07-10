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

package com.bernardomg.darksouls.explorer.test.integration.item.weapon.service;

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

import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.darksouls.explorer.item.weapon.service.DefaultWeaponService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;

@IntegrationTest
@DisplayName("Reading single weapon from id")
@Sql({ "/db/queries/weapon/single.sql" })
public class ITDefaultWeaponServiceGetOneSingle {

    @Container
    private static final MySQLContainer<?> mysqlContainer = ContainerFactory.getMysqlContainer();

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }

    @Autowired
    private WeaponRepository     repository;

    @Autowired
    private DefaultWeaponService service;

    /**
     * Default constructor.
     */
    public ITDefaultWeaponServiceGetOneSingle() {
        super();
    }

    @Test
    @DisplayName("Returns the correct damage")
    public void testGetOne_Damage() {
        final Weapon data;
        final Long   id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(8, data.getDamage()
            .getPhysical());
        Assertions.assertEquals(9, data.getDamage()
            .getMagic());
        Assertions.assertEquals(10, data.getDamage()
            .getFire());
        Assertions.assertEquals(11, data.getDamage()
            .getLightning());
        Assertions.assertEquals(12, data.getDamage()
            .getCritical());
    }

    @Test
    @DisplayName("Returns the correct damage reduction")
    public void testGetOne_DamageReduction() {
        final Weapon data;
        final Long   id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(13, data.getDamageReduction()
            .getPhysical());
        Assertions.assertEquals(14, data.getDamageReduction()
            .getMagic());
        Assertions.assertEquals(15, data.getDamageReduction()
            .getFire());
        Assertions.assertEquals(16, data.getDamageReduction()
            .getLightning());
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testGetOne_Data() {
        final Weapon data;
        final Long   id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals("Sword", data.getName());
        Assertions.assertEquals("Description", data.getDescription());
        Assertions.assertEquals("Weapon", data.getType());
        Assertions.assertEquals("Subtype", data.getSubtype());
    }

    @Test
    @DisplayName("Returns no data for a not existing id")
    public void testGetOne_NotExisting() {
        final Optional<Weapon> data;

        data = service.getOne(-1l);

        Assertions.assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Returns the correct requirements")
    public void testGetOne_Requirement() {
        final Weapon data;
        final Long   id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(4, data.getRequirements()
            .getDexterity());
        Assertions.assertEquals(5, data.getRequirements()
            .getFaith());
        Assertions.assertEquals(6, data.getRequirements()
            .getStrength());
        Assertions.assertEquals(7, data.getRequirements()
            .getIntelligence());
    }

    @Test
    @DisplayName("Returns the correct stats")
    public void testGetOne_Stats() {
        final Weapon data;
        final Long   id;

        id = getId();

        data = service.getOne(id)
            .get();

        Assertions.assertEquals(1, data.getDurability());
        Assertions.assertEquals(2, data.getWeight());
        Assertions.assertEquals(3, data.getStability());
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

}
