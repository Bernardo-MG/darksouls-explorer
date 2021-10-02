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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.bernardomg.darksouls.explorer.Application;
import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Link;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@Testcontainers
@ContextConfiguration(initializers = { ITGraphQueriesAll.Initializer.class })
@SpringBootTest(classes = Application.class)
@DisplayName("Querying all the data from the repository")
public class ITGraphQueriesAll {

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(
                final ConfigurableApplicationContext configurableApplicationContext) {

            neo4jContainer.addExposedPorts(7687);
            TestPropertyValues
                    .of("spring.neo4j.uri=" + neo4jContainer.getBoltUrl(),
                            "spring.neo4j.authentication.username=neo4j",
                            "spring.neo4j.authentication.password="
                                    + neo4jContainer.getAdminPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>(
            DockerImageName.parse("neo4j").withTag("3.5.27")).withReuse(true);

    @BeforeAll
    static void prepareTestdata() {
        final String password;
        final AuthToken auth;

        password = neo4jContainer.getAdminPassword();
        auth = AuthTokens.basic("neo4j", password);
        try (final Driver driver = GraphDatabase
                .driver(neo4jContainer.getBoltUrl(), auth);
                final Session session = driver.session()) {
            session.<Object> writeTransaction(
                    work -> work.run("CREATE ({name: 'Source'});"));
            session.<Object> writeTransaction(
                    work -> work.run("CREATE ({name: 'Target'});"));
            session.<Object> writeTransaction(work -> work.run(
                    "MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);"));
        }
    }

    @Autowired
    private GraphQueries queries;

    /**
     * Default constructor.
     */
    public ITGraphQueriesAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testFindAll_Single_Count() {
        final Graph data;

        data = queries.findAll();

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testFindAll_Single_Data() {
        final Link data;

        data = queries.findAll().getLinks().iterator().next();

        Assertions.assertEquals("Source", data.getSource());
        Assertions.assertEquals("Target", data.getTarget());
        Assertions.assertEquals("RELATIONSHIP", data.getType());
    }

}
