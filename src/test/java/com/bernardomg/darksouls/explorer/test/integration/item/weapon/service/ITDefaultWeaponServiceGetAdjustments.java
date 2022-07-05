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

package com.bernardomg.darksouls.explorer.test.integration.item.weapon.service;

import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.google.common.collect.Iterables;

import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.WeaponAdjustment;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.darksouls.explorer.item.weapon.service.DefaultWeaponService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;

@IntegrationTest
@DisplayName("Reading weapon level adjustments")
@Sql({ "/db/queries/weapon/single.sql", "/db/queries/weapon/adjustment_5.sql" })
public class ITDefaultWeaponServiceGetAdjustments {

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
    public ITDefaultWeaponServiceGetAdjustments() {
        super();
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

    @Test
    @DisplayName("Returns the level adjustments")
    public void testGetAdjustment_Data() {
        final Iterable<WeaponAdjustment> data;
        final Long                       id;

        id = getId();

        data = service.getAdjustment(id);

        Assertions.assertEquals(5, Iterables.size(data));
    }

    @Test
    @DisplayName("Returns no level adjustment when asking for a not existing weapon")
    public void testGetAdjustment_NotExisting() {
        final Iterable<WeaponAdjustment> data;

        data = service.getAdjustment(-1l);

        Assertions.assertEquals(0, Iterables.size(data));
    }

    @Test
    @DisplayName("Returns the expected values")
    public void testGetAdjustment_Values() {
        final Iterator<WeaponAdjustment> data;
        final Long                       id;
        WeaponAdjustment                 adjust;

        id = getId();

        data = service.getAdjustment(id)
            .iterator();

        adjust = data.next();

        Assertions.assertEquals("Sword", adjust.getName());
        Assertions.assertEquals(1, adjust.getFaith());
        Assertions.assertEquals(10, adjust.getIntelligence());
        Assertions.assertEquals(20, adjust.getAdjustment());

        adjust = data.next();

        Assertions.assertEquals("Sword", adjust.getName());
        Assertions.assertEquals(2, adjust.getFaith());
        Assertions.assertEquals(11, adjust.getIntelligence());
        Assertions.assertEquals(21, adjust.getAdjustment());

        adjust = data.next();

        Assertions.assertEquals("Sword", adjust.getName());
        Assertions.assertEquals(3, adjust.getFaith());
        Assertions.assertEquals(12, adjust.getIntelligence());
        Assertions.assertEquals(22, adjust.getAdjustment());

        adjust = data.next();

        Assertions.assertEquals("Sword", adjust.getName());
        Assertions.assertEquals(4, adjust.getFaith());
        Assertions.assertEquals(13, adjust.getIntelligence());
        Assertions.assertEquals(23, adjust.getAdjustment());

        adjust = data.next();

        Assertions.assertEquals("Sword", adjust.getName());
        Assertions.assertEquals(5, adjust.getFaith());
        Assertions.assertEquals(14, adjust.getIntelligence());
        Assertions.assertEquals(24, adjust.getAdjustment());
    }

}
