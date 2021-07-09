/**
 * Copyright 2020 the original author or authors
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

package com.bernardomg.darksouls.explorer.test.integration.persistence;

import java.util.Iterator;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.darksouls.explorer.Application;
import com.bernardomg.darksouls.explorer.model.Item;
import com.bernardomg.darksouls.explorer.persistence.repository.GraphRepository;

import graphql.com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphRepository}.
 */
@SpringJUnitConfig
@Transactional(propagation = Propagation.NEVER)
@Rollback
@SpringBootTest(classes = Application.class)
@DisplayName("Querying the repository by id with a description")
public class ITGraphRepositoryFindByIdDescription {

    private static Neo4j embeddedDatabaseServer;

    @BeforeAll
    public static void initializeNeo4j() {
        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()// disable http server
                .withFixture("CREATE ({name: 'Source'});")
                .withFixture(
                        "CREATE ({name: 'Target', description: 'line1|line2'});")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);")
                .build();
    }

    @DynamicPropertySource
    public static void neo4jProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", embeddedDatabaseServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> null);
    }

    @AfterAll
    public static void stopNeo4j() {
        embeddedDatabaseServer.close();
    }

    @Autowired
    private GraphRepository repository;

    /**
     * Default constructor.
     */
    public ITGraphRepositoryFindByIdDescription() {
        super();
    }

    @Test
    @DisplayName("Returns no data for a not existing id")
    public void testFindAll_NotExisting() {
        final Optional<Item> data;

        data = repository.findById(12345);

        Assertions.assertTrue(data.isEmpty());
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testFindAll_Single_Data() {
        final Optional<Item> data;
        final Iterator<String> itr;

        data = repository.findById(1);

        Assertions.assertEquals(1l, data.get().getId());
        Assertions.assertEquals("Target", data.get().getName());
        Assertions.assertEquals(2, Iterables.size(data.get().getDescription()));

        itr = data.get().getDescription().iterator();
        Assertions.assertEquals("line1", itr.next());
        Assertions.assertEquals("line2", itr.next());
    }

}
