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

package com.bernardomg.darksouls.explorer.test.integration.graph.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Link;
import com.bernardomg.darksouls.explorer.graph.model.Node;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(
        initializers = { ITGraphQueriesByLinkType.Initializer.class })
@DisplayName("Querying the repository filtering by type")
public class ITGraphQueriesByLinkType {

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
            Arrays.asList("classpath:db/queries/graph/simple.cypher"));
    }

    @Autowired
    private GraphQueries queries;

    /**
     * Default constructor.
     */
    public ITGraphQueriesByLinkType() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for a single type")
    public void testFindAllByLinkType_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP"));

        Assertions.assertEquals(1, IterableUtils.size(data.getLinks()));
        Assertions.assertEquals(2, IterableUtils.size(data.getNodes()));
        Assertions.assertEquals(1, IterableUtils.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns the correct data a single type")
    public void testFindAllByLinkType_Data() {
        final Link data;

        data = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP"))
            .getLinks()
            .iterator()
            .next();

        Assertions.assertEquals("Source", data.getSourceLabel());
        Assertions.assertEquals("Target", data.getTargetLabel());
        Assertions.assertEquals("RELATIONSHIP", data.getType());
    }

    @Test
    @DisplayName("Returns the correct links")
    public void testFindAllByLinkType_Data_Links() {
        final Link data;

        data = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP"))
            .getLinks()
            .iterator()
            .next();

        Assertions.assertEquals("Source", data.getSourceLabel());
        Assertions.assertEquals("Target", data.getTargetLabel());
        Assertions.assertEquals("RELATIONSHIP", data.getType());
    }

    @Test
    @DisplayName("Returns the correct nodes")
    public void testFindAllByLinkType_Data_Nodes() {
        final Iterator<Node> nodes;
        Node data;

        nodes = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP"))
            .getNodes()
            .iterator();

        data = nodes.next();
        Assertions.assertEquals("Source", data.getLabel());

        data = nodes.next();
        Assertions.assertEquals("Target", data.getLabel());
    }

    @Test
    @DisplayName("Returns the correct types")
    public void testFindAllByLinkType_Data_Types() {
        final Iterator<String> types;
        String type;

        types = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP"))
            .getTypes()
            .iterator();

        type = types.next();
        Assertions.assertEquals("RELATIONSHIP", type);
    }

    @Test
    @DisplayName("Returns no data for an empty type list")
    public void testFindAllByLinkType_Empty_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Collections.emptyList());

        Assertions.assertEquals(0, IterableUtils.size(data.getLinks()));
        Assertions.assertEquals(0, IterableUtils.size(data.getNodes()));
        Assertions.assertEquals(0, IterableUtils.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns all the data for multiple types, one of them not existing")
    public void testFindAllByLinkType_IncludesNotExisting_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Arrays.asList("RELATIONSHIP", "ABC"));

        Assertions.assertEquals(1, IterableUtils.size(data.getLinks()));
        Assertions.assertEquals(2, IterableUtils.size(data.getNodes()));
        Assertions.assertEquals(1, IterableUtils.size(data.getTypes()));
    }

    @Test
    @DisplayName("Returns all the data for a not existing type")
    public void testFindAllByLinkType_NotExisting_Count() {
        final Graph data;

        data = queries.findAllByLinkType(Arrays.asList("ABC"));

        Assertions.assertEquals(0, IterableUtils.size(data.getLinks()));
        Assertions.assertEquals(0, IterableUtils.size(data.getNodes()));
        Assertions.assertEquals(0, IterableUtils.size(data.getTypes()));
    }

    @Test
    @DisplayName("Rejects a null value")
    public void testFindAllByLinkType_Null() {
        Assertions.assertThrows(NullPointerException.class,
            () -> queries.findAllByLinkType(null));
    }

}
