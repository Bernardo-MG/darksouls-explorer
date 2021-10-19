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

package com.bernardomg.darksouls.explorer.test.integration.persistence;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neo4j.cypherdsl.core.AliasedExpression;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.bernardomg.darksouls.explorer.Application;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.item.model.DefaultItem;
import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@Testcontainers
@ContextConfiguration(
        initializers = { ITQueryExecutorPagination.Initializer.class })
@SpringBootTest(classes = Application.class)
@DisplayName("Reading all the items")
public class ITQueryExecutorPagination {

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
    private static final Neo4jContainer<?> neo4jContainer = ContainerFactory
            .getNeo4jContainer();

    @BeforeAll
    private static void prepareTestdata() {
        new Neo4jDatabaseInitalizer().initialize("neo4j",
                neo4jContainer.getAdminPassword(), neo4jContainer.getBoltUrl(),
                Arrays.asList("classpath:db/queries/item/multiple.cypher"));
    }

    private QueryExecutor queryExecutor;

    @Autowired
    public ITQueryExecutorPagination(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt,
                Renderer.getDefaultRenderer());
    }

    @Test
    @DisplayName("Returns all the data when not paginated")
    public void testFetch_NoPagination() {
        final Page<Item> data;
        final Pageable page;
        final BuildableStatement<ResultStatement> statementBuilder;

        page = Pageable.unpaged();

        statementBuilder = getStatementBuilder();

        data = queryExecutor.fetch(statementBuilder, this::toItem, page);

        Assertions.assertEquals(5, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(1, data.getTotalPages());
        Assertions.assertEquals(0, data.getNumber());
    }

    @Test
    @DisplayName("Sorts in ascending order")
    public void testFetch_Order_Ascending() {
        final Iterator<Item> data;
        final Pageable page;
        final BuildableStatement<ResultStatement> statementBuilder;

        page = PageRequest.of(0, 5, Direction.ASC, "name");

        statementBuilder = getStatementBuilder();

        data = queryExecutor.fetch(statementBuilder, this::toItem, page)
                .iterator();

        Assertions.assertEquals("Item1", data.next().getName());
        Assertions.assertEquals("Item2", data.next().getName());
        Assertions.assertEquals("Item3", data.next().getName());
        Assertions.assertEquals("Item4", data.next().getName());
        Assertions.assertEquals("Item5", data.next().getName());
    }

    @Test
    @DisplayName("Sorts in descending order")
    public void testFetch_Order_Descending() {
        final Iterator<Item> data;
        final Pageable page;
        final BuildableStatement<ResultStatement> statementBuilder;

        page = PageRequest.of(0, 5, Direction.DESC, "name");

        statementBuilder = getStatementBuilder();

        data = queryExecutor.fetch(statementBuilder, this::toItem, page)
                .iterator();

        Assertions.assertEquals("Item5", data.next().getName());
        Assertions.assertEquals("Item4", data.next().getName());
        Assertions.assertEquals("Item3", data.next().getName());
        Assertions.assertEquals("Item2", data.next().getName());
        Assertions.assertEquals("Item1", data.next().getName());
    }

    @Test
    @DisplayName("Reads first page")
    public void testFetch_Pagination_FirstPage() {
        final Page<Item> data;
        final Pageable page;
        final BuildableStatement<ResultStatement> statementBuilder;

        page = Pageable.ofSize(1);

        statementBuilder = getStatementBuilder();

        data = queryExecutor.fetch(statementBuilder, this::toItem, page);

        Assertions.assertEquals(1, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(5, data.getTotalPages());
        Assertions.assertEquals(0, data.getNumber());
    }

    @Test
    @DisplayName("Reads second page")
    public void testFetch_Pagination_SecondPage() {
        final Page<Item> data;
        final Pageable page;
        final BuildableStatement<ResultStatement> statementBuilder;

        page = PageRequest.of(1, 1);

        statementBuilder = getStatementBuilder();

        data = queryExecutor.fetch(statementBuilder, this::toItem, page);

        Assertions.assertEquals(1, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(5, data.getTotalPages());
        Assertions.assertEquals(1, data.getNumber());
    }

    private final BuildableStatement<ResultStatement> getStatementBuilder() {
        final Node item;
        final AliasedExpression name;

        item = Cypher.node("Item").named("i");
        name = item.property("name").as("name");
        return Cypher.match(item).returning(name,
                item.property("description").as("description"));
    }

    private final Item toItem(final Map<String, Object> record) {
        final Item item;

        item = new DefaultItem((String) record.getOrDefault("name", ""),
                (String) record.getOrDefault("description", ""));

        return item;
    }

}
