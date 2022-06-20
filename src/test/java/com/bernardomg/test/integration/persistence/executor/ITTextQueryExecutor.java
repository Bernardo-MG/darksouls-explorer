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

package com.bernardomg.test.integration.persistence.executor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.collections4.IterableUtils;
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

import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;
import com.bernardomg.darksouls.explorer.test.util.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.test.util.domain.Item;
import com.bernardomg.persistence.executor.QueryExecutor;
import com.bernardomg.persistence.executor.TextQueryExecutor;

@IntegrationTest
@ContextConfiguration(initializers = { ITTextQueryExecutor.Initializer.class })
@DisplayName("Query executor")
public class ITTextQueryExecutor {

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
    public ITTextQueryExecutor(final Neo4jClient clnt) {
        super();

        queryExecutor = new TextQueryExecutor(clnt);
    }

    @Test
    @DisplayName("Reads the content for a query")
    public void testFetch_Content() {
        final Iterator<Item> data;

        data = queryExecutor.fetch(getQuery(), this::toItem)
            .iterator();

        Assertions.assertEquals("Item1", data.next()
            .getName());
        Assertions.assertEquals("Item2", data.next()
            .getName());
        Assertions.assertEquals("Item3", data.next()
            .getName());
        Assertions.assertEquals("Item4", data.next()
            .getName());
        Assertions.assertEquals("Item5", data.next()
            .getName());
    }

    @Test
    @DisplayName("Reads the values for a query")
    public void testFetch_Values() {
        final Collection<Item> data;

        data = queryExecutor.fetch(getQuery(), this::toItem);

        Assertions.assertEquals(5, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Reads the values for a query of a single value")
    public void testFetchOne_Values() {
        final Optional<Item> data;

        data = queryExecutor.fetchOne(getQuery(), this::toItem);

        Assertions.assertTrue(data.isPresent());
    }

    private final Function<Map<String, Object>, String> getQuery() {
        return (m) -> "MATCH (i:Item) RETURN i.name AS name, i.description AS description";
    }

    @SuppressWarnings("unchecked")
    private final Item toItem(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final Iterable<String> tags;

        // TODO: Use custom model for testing

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));
        tags = (Iterable<String>) record.getOrDefault("labels",
            Collections.emptyList());

        return new ImmutableItem(id, name, description, tags);
    }

}
