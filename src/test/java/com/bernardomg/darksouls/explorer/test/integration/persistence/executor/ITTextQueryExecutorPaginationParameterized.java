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

package com.bernardomg.darksouls.explorer.test.integration.persistence.executor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

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

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemRequirements;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.Item;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.executor.TextQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.DefaultPagination;
import com.bernardomg.darksouls.explorer.persistence.model.DefaultSort;
import com.bernardomg.darksouls.explorer.persistence.model.Direction;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

@IntegrationTest
@ContextConfiguration(initializers = {
        ITTextQueryExecutorPaginationParameterized.Initializer.class })
@DisplayName("Query executor paginated and parameterized")
public class ITTextQueryExecutorPaginationParameterized {

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
    public ITTextQueryExecutorPaginationParameterized(final Neo4jClient clnt) {
        super();

        queryExecutor = new TextQueryExecutor(clnt);
    }

    @Test
    @DisplayName("Reads the content for a page returning a single value")
    public void testFetch_AllElementsPage_Content() {
        final Iterator<Item> data;
        final Pagination pagination;
        final Sort sort;
        final Map<String, Object> parameters;

        parameters = new HashMap<>();
        parameters.put("name", "Item1");

        pagination = new DefaultPagination(0, 5);
        sort = new DefaultSort("name", Direction.ASC);

        data = queryExecutor
            .fetch(getQuery(), this::toItem, parameters, pagination,
                Arrays.asList(sort))
            .iterator();

        Assertions.assertEquals("Item1", data.next()
            .getName());
    }

    @Test
    @DisplayName("Reads the status for a page returning a single value")
    public void testFetch_AllElementsPage_Status() {
        final PageIterable<Item> data;
        final Pagination pagination;
        final Sort sort;
        final Map<String, Object> parameters;

        parameters = new HashMap<>();
        parameters.put("name", "Item1");

        pagination = new DefaultPagination(0, 5);
        sort = new DefaultSort("name", Direction.ASC);

        data = queryExecutor.fetch(getQuery(), this::toItem, parameters,
            pagination, Arrays.asList(sort));

        Assertions.assertTrue(data.isFirst());
        Assertions.assertTrue(data.isLast());
    }

    @Test
    @DisplayName("Reads the values for a page returning a single value")
    public void testFetch_AllElementsPage_Values() {
        final PageIterable<Item> data;
        final Pagination pagination;
        final Sort sort;
        final Map<String, Object> parameters;

        parameters = new HashMap<>();
        parameters.put("name", "Item1");

        pagination = new DefaultPagination(0, 5);
        sort = new DefaultSort("name", Direction.ASC);

        data = queryExecutor.fetch(getQuery(), this::toItem, parameters,
            pagination, Arrays.asList(sort));

        Assertions.assertEquals(5, data.getSize());
        Assertions.assertEquals(1, data.getTotalElements());
        Assertions.assertEquals(1, data.getTotalPages());
        Assertions.assertEquals(0, data.getPageNumber());
    }

    private final Function<Map<String, Object>, String> getQuery() {
        return (m) -> "MATCH (i:Item) WHERE i.name = $name RETURN i.name AS name, i.description AS description";
    }

    @SuppressWarnings("unchecked")
    private final Item toItem(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final Iterable<String> tags;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));
        tags = (Iterable<String>) record.getOrDefault("labels",
            Collections.emptyList());

        return new ImmutableItem(id, name,
            new ImmutableItemRequirements(0, 0, 0, 0),
            new ImmutableItemStats(0l, 0), description, tags);
    }

}
