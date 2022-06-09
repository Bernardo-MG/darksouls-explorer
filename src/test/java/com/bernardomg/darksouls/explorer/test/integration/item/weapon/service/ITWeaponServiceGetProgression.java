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

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.darksouls.explorer.item.weapon.service.WeaponService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

@IntegrationTest
@ContextConfiguration(
        initializers = { ITWeaponServiceGetProgression.Initializer.class })
@DisplayName("Reading weapon progression")
@Sql({ "/db/queries/weapon/single.sql",
        "/db/queries/weapon/physical_5_levels.sql" })
public class ITWeaponServiceGetProgression {

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

    @BeforeAll
    private static void prepareTestdata() {
        new Neo4jDatabaseInitalizer().initialize("neo4j",
            neo4jContainer.getAdminPassword(), neo4jContainer.getBoltUrl(),
            Arrays.asList(
                "classpath:db/queries/weapon/physical_5_levels.cypher"));
    }

    @Autowired
    private WeaponRepository repository;

    @Autowired
    private WeaponService    service;

    /**
     * Default constructor.
     */
    public ITWeaponServiceGetProgression() {
        super();
    }

    @Test
    @DisplayName("Returns the levels progression")
    public void testgetProgression_Data() {
        final Optional<WeaponProgression> data;
        final Long id;

        id = getId();

        data = service.getProgression(id);

        Assertions.assertTrue(data.isPresent());
    }

    @Test
    @DisplayName("Returns the levels bonuses")
    public void testgetProgression_LevelsBonus() {
        final WeaponProgression data;
        final Iterator<WeaponProgressionLevel> levels;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;
        WeaponProgressionLevel level;

        id = getId();

        data = service.getProgression(id)
            .get();

        itr = data.getPaths()
            .iterator();
        itr.next();
        path = itr.next();
        levels = path.getLevels()
            .iterator();

        level = levels.next();
        Assertions.assertEquals("A", level.getStrengthBonus());
        Assertions.assertEquals("B", level.getDexterityBonus());
        Assertions.assertEquals("C", level.getIntelligenceBonus());
        Assertions.assertEquals("D", level.getFaithBonus());
    }

    @Test
    @DisplayName("Returns the levels damage")
    public void testgetProgression_LevelsDamage() {
        final WeaponProgression data;
        final Iterator<WeaponProgressionLevel> levels;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;
        WeaponProgressionLevel level;

        id = getId();

        data = service.getProgression(id)
            .get();

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

    @Test
    @DisplayName("Returns the levels in order")
    public void testgetProgression_LevelsOrder() {
        final WeaponProgression data;
        final Iterator<WeaponProgressionLevel> levels;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;
        WeaponProgressionLevel level;

        id = getId();

        data = service.getProgression(id)
            .get();

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
    @DisplayName("Returns the levels reductions")
    public void testgetProgression_LevelsReductions() {
        final WeaponProgression data;
        final Iterator<WeaponProgressionLevel> levels;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;
        WeaponProgressionLevel level;

        id = getId();

        data = service.getProgression(id)
            .get();

        itr = data.getPaths()
            .iterator();
        itr.next();
        path = itr.next();
        levels = path.getLevels()
            .iterator();

        level = levels.next();
        Assertions.assertEquals(0.1f, level.getPhysicalReduction());
        Assertions.assertEquals(0.2f, level.getMagicReduction());
        Assertions.assertEquals(0.3f, level.getFireReduction());
        Assertions.assertEquals(0.4f, level.getLightningReduction());
    }

    @Test
    @DisplayName("Returns the path level")
    public void testgetProgression_PathLevel() {
        final WeaponProgression data;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionLevel level;
        WeaponProgressionPath path;

        id = getId();

        data = service.getProgression(id)
            .get();

        itr = data.getPaths()
            .iterator();

        path = itr.next();

        path = itr.next();

        level = path.getLevels()
            .iterator()
            .next();
        Assertions.assertEquals(0, level.getPathLevel());
    }

    @Test
    @DisplayName("Returns the expected structure")
    public void testgetProgression_Structure() {
        final WeaponProgression data;
        final Long id;
        final Iterator<WeaponProgressionPath> itr;
        WeaponProgressionPath path;

        id = getId();

        data = service.getProgression(id)
            .get();

        Assertions.assertEquals("Sword", data.getWeapon());
        Assertions.assertEquals(2, IterableUtils.size(data.getPaths()));

        itr = data.getPaths()
            .iterator();
        path = itr.next();

        Assertions.assertEquals("Magic", path.getPath());
        Assertions.assertEquals(0, IterableUtils.size(path.getLevels()));

        path = itr.next();

        Assertions.assertEquals("Physical", path.getPath());
        Assertions.assertEquals(5, IterableUtils.size(path.getLevels()));
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

}
