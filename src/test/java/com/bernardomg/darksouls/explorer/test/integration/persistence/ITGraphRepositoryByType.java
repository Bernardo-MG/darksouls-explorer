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

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.darksouls.explorer.Application;
import com.bernardomg.darksouls.explorer.model.Graph;
import com.bernardomg.darksouls.explorer.model.Link;
import com.bernardomg.darksouls.explorer.persistence.repository.GraphRepository;
import com.bernardomg.darksouls.explorer.test.integration.common.Neo4jTestData;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphRepository}.
 */
@SpringJUnitConfig
@Transactional(propagation = Propagation.NEVER)
@Rollback
@SpringBootTest(classes = Application.class)
@DisplayName("Querying the repository filtering by type")
public class ITGraphRepositoryByType {

    private static Neo4j embeddedDatabaseServer;

    @BeforeAll
    public static void initializeNeo4j() {
        embeddedDatabaseServer = Neo4jTestData.getSimple();
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
    public ITGraphRepositoryByType() {
        super();
    }

    @Test
    @DisplayName("Returns no data for an empty type list")
    public void testFindAll_Empty_Count() {
        final Graph data;

        data = repository.findAllByLinkType(Collections.emptyList());

        Assertions.assertEquals(0, Iterables.size(data.getLinks()));
        Assertions.assertEquals(0, Iterables.size(data.getNodes()));
        Assertions.assertEquals(0, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns all the data for multiple types, one of them not existing")
    public void testFindAll_Multiple_NotExisting_Count() {
        final Graph data;

        data = repository
                .findAllByLinkType(Arrays.asList("RELATIONSHIP", "ABC"));

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Rejects a null value")
    public void testFindAll_Null() {
        Assertions.assertThrows(NullPointerException.class,
                () -> repository.findAllByLinkType(null));
    }

    @Test
    @DisplayName("Returns all the data for a single type")
    public void testFindAll_Single_Count() {
        final Graph data;

        data = repository.findAllByLinkType(Arrays.asList("RELATIONSHIP"));

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns the correct data a single type")
    public void testFindAll_Single_Data() {
        final Link data;

        data = repository.findAllByLinkType(Arrays.asList("RELATIONSHIP"))
                .getLinks().iterator().next();

        Assertions.assertEquals("Source", data.getSource());
        Assertions.assertEquals("Target", data.getTarget());
        Assertions.assertEquals("RELATIONSHIP", data.getType());
    }

}
