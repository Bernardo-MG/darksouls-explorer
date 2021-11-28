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
import org.springframework.beans.factory.annotation.Autowired;
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

import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;
import com.bernardomg.darksouls.explorer.item.model.DefaultItem;
import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

/**
 * Integration tests for the {@link GraphQueries}.
 */
@IntegrationTest
@ContextConfiguration(
        initializers = { ITQueryExecutorPagination.Initializer.class })
@DisplayName("Reading all the items")
public class ITQueryExecutorPagination {

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
                Arrays.asList("classpath:db/queries/item/multiple.cypher"));
    }

    private final QueryExecutor queryExecutor;

    @Autowired
    public ITQueryExecutorPagination(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Test
    @DisplayName("Returns all the data when not paginated through a query")
    public void testFetch_Query_NoPagination() {
        final Page<Item> data;
        final Pageable page;

        page = Pageable.unpaged();

        data = queryExecutor.fetch(getQuery(), this::toItem, page);

        Assertions.assertEquals(5, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(1, data.getTotalPages());
        Assertions.assertEquals(0, data.getNumber());
    }

    @Test
    @DisplayName("Sorts in ascending order through a query")
    public void testFetch_Query_Order_Ascending() {
        final Iterator<Item> data;
        final Pageable page;

        page = PageRequest.of(0, 5, Direction.ASC, "name");

        data = queryExecutor.fetch(getQuery(), this::toItem, page).iterator();

        Assertions.assertEquals("Item1", data.next().getName());
        Assertions.assertEquals("Item2", data.next().getName());
        Assertions.assertEquals("Item3", data.next().getName());
        Assertions.assertEquals("Item4", data.next().getName());
        Assertions.assertEquals("Item5", data.next().getName());
    }

    @Test
    @DisplayName("Sorts in descending order through a query")
    public void testFetch_Query_Order_Descending() {
        final Iterator<Item> data;
        final Pageable page;

        page = PageRequest.of(0, 5, Direction.DESC, "name");

        data = queryExecutor.fetch(getQuery(), this::toItem, page).iterator();

        Assertions.assertEquals("Item5", data.next().getName());
        Assertions.assertEquals("Item4", data.next().getName());
        Assertions.assertEquals("Item3", data.next().getName());
        Assertions.assertEquals("Item2", data.next().getName());
        Assertions.assertEquals("Item1", data.next().getName());
    }

    @Test
    @DisplayName("Reads first page through a query")
    public void testFetch_Query_Pagination_FirstPage() {
        final Page<Item> data;
        final Pageable page;

        page = PageRequest.of(0, 1, Direction.ASC, "name");

        data = queryExecutor.fetch(getQuery(), this::toItem, page);

        Assertions.assertEquals(1, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(5, data.getTotalPages());
        Assertions.assertEquals(0, data.getNumber());

        Assertions.assertEquals("Item1", data.iterator().next().getName());
    }

    @Test
    @DisplayName("Reads second page through a query")
    public void testFetch_Query_Pagination_SecondPage() {
        final Page<Item> data;
        final Pageable page;

        page = PageRequest.of(1, 1, Direction.ASC, "name");

        data = queryExecutor.fetch(getQuery(), this::toItem, page);

        Assertions.assertEquals(1, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(5, data.getTotalPages());
        Assertions.assertEquals(1, data.getNumber());

        Assertions.assertEquals("Item2", data.iterator().next().getName());
    }

    @Test
    @DisplayName("Returns all the data when not paginated through an statement")
    public void testFetch_Statement_NoPagination() {
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
    @DisplayName("Sorts in ascending order through an statement")
    public void testFetch_Statement_Order_Ascending() {
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
    @DisplayName("Sorts in descending order through an statement")
    public void testFetch_Statement_Order_Descending() {
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
    @DisplayName("Reads first page through an statement")
    public void testFetch_Statement_Pagination_FirstPage() {
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

        Assertions.assertEquals("Item1", data.iterator().next().getName());
    }

    @Test
    @DisplayName("Reads second page through an statement")
    public void testFetch_Statement_Pagination_SecondPage() {
        final Page<Item> data;
        final Pageable page;
        final BuildableStatement<ResultStatement> statementBuilder;

        page = PageRequest.of(1, 1, Direction.ASC, "name");

        statementBuilder = getStatementBuilder();

        data = queryExecutor.fetch(statementBuilder, this::toItem, page);

        Assertions.assertEquals(1, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(5, data.getTotalPages());
        Assertions.assertEquals(1, data.getNumber());

        Assertions.assertEquals("Item2", data.iterator().next().getName());
    }

    private final String getQuery() {
        return "MATCH (i:Item) RETURN i.name AS name, i.description AS description";
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
        final Long id;
        final String name;
        final Iterable<String> description;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
                ((String) record.getOrDefault("description", "")).split("\\|"));

        return new DefaultItem(id, name, description);
    }

}
