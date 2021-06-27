/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.darksouls.explorer.test.integration.persistence;

import java.util.Arrays;

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
import com.bernardomg.darksouls.explorer.model.Graph;
import com.bernardomg.darksouls.explorer.persistence.repository.GraphRepository;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphRepository}.
 */
@SpringJUnitConfig
@Transactional(propagation = Propagation.NEVER)
@Rollback
@SpringBootTest(classes = Application.class)
public class ITGraphRepositoryMultiple {

    private static Neo4j embeddedDatabaseServer;

    @BeforeAll
    public static void initializeNeo4j() {
        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()// disable http server
                .withFixture("CREATE ({name: 'Source'});")
                .withFixture("CREATE ({name: 'Target'});")
                .withFixture("CREATE ({name: 'Another'});")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Another'}) MERGE (n)-[:RELATIONSHIP]->(m);")
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
    public ITGraphRepositoryMultiple() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testFindAll_Count() {
        final Graph data;

        data = repository.findAllByLinkType(Arrays.asList("RELATIONSHIP"));

        Assertions.assertEquals(2, Iterables.size(data.getLinks()));
        Assertions.assertEquals(3, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

}
