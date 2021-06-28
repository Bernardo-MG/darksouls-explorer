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

package com.bernardomg.darksouls.explorer.test.integration.graphql;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import com.bernardomg.darksouls.explorer.graphql.GraphDataFetcher;
import com.bernardomg.darksouls.explorer.model.Graph;
import com.bernardomg.darksouls.explorer.model.Link;
import com.bernardomg.darksouls.explorer.persistence.repository.GraphRepository;
import com.google.common.collect.Iterables;

import graphql.schema.DataFetchingEnvironment;

/**
 * Integration tests for the {@link GraphRepository}.
 */
@SpringJUnitConfig
@Transactional(propagation = Propagation.NEVER)
@Rollback
@SpringBootTest(classes = Application.class)
public class ITGraphDataFetcher {

    private static Neo4j embeddedDatabaseServer;

    @BeforeAll
    public static void initializeNeo4j() {
        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()// disable http server
                .withFixture("CREATE ({name: 'Source'});")
                .withFixture("CREATE ({name: 'Target'});")
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
    private GraphDataFetcher fetcher;

    /**
     * Default constructor.
     */
    public ITGraphDataFetcher() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for an empty type list")
    public void testGet_EmptyType_Count() throws Exception {
        final Graph data;
        final DataFetchingEnvironment environment;
        final Iterable<String> types;

        types = Collections.emptyList();

        environment = Mockito.mock(DataFetchingEnvironment.class);
        Mockito.when(environment.getArgumentOrDefault(Mockito.matches("type"),
                Mockito.any())).thenReturn(types);

        data = fetcher.get(environment);

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns all the data for multiple types, one of them not existing")
    public void testGet_MultipleTypes_NotExisting_Count() throws Exception {
        final Graph data;
        final DataFetchingEnvironment environment;
        final Iterable<String> types;

        types = Arrays.asList("RELATIONSHIP", "ABC");

        environment = Mockito.mock(DataFetchingEnvironment.class);
        Mockito.when(environment.getArgumentOrDefault(Mockito.matches("type"),
                Mockito.any())).thenReturn(types);

        data = fetcher.get(environment);

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @Disabled("Handled by the environment implementation")
    @DisplayName("Returns all the data when there is no type")
    public void testGet_NoType_Count() throws Exception {
        final Graph data;
        final DataFetchingEnvironment environment;

        environment = Mockito.mock(DataFetchingEnvironment.class);

        data = fetcher.get(environment);

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns all the data for a single type")
    public void testGet_SingleType_Count() throws Exception {
        final Graph data;
        final DataFetchingEnvironment environment;
        final Iterable<String> types;

        types = Arrays.asList("RELATIONSHIP");

        environment = Mockito.mock(DataFetchingEnvironment.class);
        Mockito.when(environment.getArgumentOrDefault(Mockito.matches("type"),
                Mockito.any())).thenReturn(types);

        data = fetcher.get(environment);

        Assertions.assertEquals(1, Iterables.size(data.getLinks()));
        Assertions.assertEquals(2, Iterables.size(data.getNodes()));
        Assertions.assertEquals(1, Iterables.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns the correct data for a single type")
    public void testGet_SingleType_Data() throws Exception {
        final Graph data;
        final Link link;
        final DataFetchingEnvironment environment;
        final Iterable<String> types;

        types = Arrays.asList("RELATIONSHIP");

        environment = Mockito.mock(DataFetchingEnvironment.class);
        Mockito.when(environment.getArgumentOrDefault(Mockito.matches("type"),
                Mockito.any())).thenReturn(types);

        data = fetcher.get(environment);
        link = data.getLinks().iterator().next();

        Assertions.assertEquals("Source", link.getSource());
        Assertions.assertEquals("Target", link.getTarget());
        Assertions.assertEquals("RELATIONSHIP", link.getType());
    }

}
