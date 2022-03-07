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
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.service.WeaponService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

@IntegrationTest
@ContextConfiguration(
        initializers = { ITWeaponServiceGetWeaponLevels.Initializer.class })
@DisplayName("Reading weapon levels")
public class ITWeaponServiceGetWeaponLevels {

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
                "classpath:db/queries/weapon/physical_5_levels.cypher"));
    }

    @Autowired
    private Neo4jClient   client;

    @Autowired
    private WeaponService service;

    /**
     * Default constructor.
     */
    public ITWeaponServiceGetWeaponLevels() {
        super();
    }

    @Test
    @DisplayName("Returns the expected structure")
    public void testGetAll_Structure() {
        final WeaponProgression data;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;

        id = getId();

        data = service.getWeaponLevels(id);

        Assertions.assertEquals("Sword", data.getWeapon());
        Assertions.assertEquals(2, IterableUtils.size(data.getPaths()));

        itr = data.getPaths()
            .iterator();
        path = itr.next();

        Assertions.assertEquals("Magic", path.getPath());
        Assertions.assertEquals(1, IterableUtils.size(path.getLevels()));

        path = itr.next();

        Assertions.assertEquals("Physical", path.getPath());
        Assertions.assertEquals(5, IterableUtils.size(path.getLevels()));
    }

    @Test
    @DisplayName("Returns the path level")
    public void testGetAll_PathLevel() {
        final WeaponProgression data;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponLevel level;
        WeaponProgressionPath path;

        id = getId();

        data = service.getWeaponLevels(id);

        itr = data.getPaths()
            .iterator();

        path = itr.next();

        level = path.getLevels()
            .iterator()
            .next();
        Assertions.assertEquals(5, level.getPathLevel());

        path = itr.next();

        level = path.getLevels()
            .iterator()
            .next();
        Assertions.assertEquals(0, level.getPathLevel());
    }

    @Test
    @DisplayName("Returns the levels in order")
    public void testGetAll_LevelsOrder() {
        final WeaponProgression data;
        final Iterator<WeaponLevel> levels;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;
        WeaponLevel level;

        id = getId();

        data = service.getWeaponLevels(id);

        itr = data.getPaths()
            .iterator();
        itr.next();
        path = itr.next();
        levels = path.getLevels()
            .iterator();

        level = levels.next();
        Assertions.assertEquals(0, level.getLevel());

        level = levels.next();
        Assertions.assertEquals(1, level.getLevel());

        level = levels.next();
        Assertions.assertEquals(2, level.getLevel());

        level = levels.next();
        Assertions.assertEquals(3, level.getLevel());

        level = levels.next();
        Assertions.assertEquals(4, level.getLevel());
    }

    @Test
    @DisplayName("Returns the levels damage")
    public void testGetAll_LevelsDamage() {
        final WeaponProgression data;
        final Iterator<WeaponLevel> levels;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;
        WeaponLevel level;

        id = getId();

        data = service.getWeaponLevels(id);

        itr = data.getPaths()
            .iterator();
        itr.next();
        path = itr.next();
        levels = path.getLevels()
            .iterator();

        level = levels.next();
        Assertions.assertEquals(10, level.getPhysicalDamage());
        Assertions.assertEquals(11, level.getMagicDamage());
        Assertions.assertEquals(12, level.getFireDamage());
        Assertions.assertEquals(13, level.getLightningDamage());

        level = levels.next();
        Assertions.assertEquals(20, level.getPhysicalDamage());
        Assertions.assertEquals(21, level.getMagicDamage());
        Assertions.assertEquals(22, level.getFireDamage());
        Assertions.assertEquals(23, level.getLightningDamage());

        level = levels.next();
        Assertions.assertEquals(30, level.getPhysicalDamage());
        Assertions.assertEquals(31, level.getMagicDamage());
        Assertions.assertEquals(32, level.getFireDamage());
        Assertions.assertEquals(33, level.getLightningDamage());

        level = levels.next();
        Assertions.assertEquals(40, level.getPhysicalDamage());
        Assertions.assertEquals(41, level.getMagicDamage());
        Assertions.assertEquals(42, level.getFireDamage());
        Assertions.assertEquals(43, level.getLightningDamage());

        level = levels.next();
        Assertions.assertEquals(50, level.getPhysicalDamage());
        Assertions.assertEquals(51, level.getMagicDamage());
        Assertions.assertEquals(52, level.getFireDamage());
        Assertions.assertEquals(53, level.getLightningDamage());
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
