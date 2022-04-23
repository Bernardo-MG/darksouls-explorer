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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import com.bernardomg.darksouls.explorer.item.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemRequirements;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.executor.TextQueryExecutor;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.context.Neo4jApplicationContextInitializer;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.darksouls.explorer.test.configuration.db.Neo4jDatabaseInitalizer;

@IntegrationTest
@ContextConfiguration(
        initializers = { ITTextQueryExecutorParameterized.Initializer.class })
@DisplayName("Query executor parameterized")
public class ITTextQueryExecutorParameterized {

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

    private final QueryExecutor<String> queryExecutor;

    @Autowired
    public ITTextQueryExecutorParameterized(final Neo4jClient clnt) {
        super();

        queryExecutor = new TextQueryExecutor(clnt);
    }

    @Test
    @DisplayName("Reads the content for a page covering all the data")
    public void testFetch_Content() {
        final Iterator<Item> data;
        final Map<String, Object> parameters;

        parameters = new HashMap<>();
        parameters.put("name", "Item1");

        data = queryExecutor.fetch(getQuery(), this::toItem, parameters)
            .iterator();

        Assertions.assertEquals("Item1", data.next()
            .getName());
    }

    @Test
    @DisplayName("Reads the values for a page covering all the data")
    public void testFetch_Values() {
        final Collection<Item> data;
        final Map<String, Object> parameters;

        parameters = new HashMap<>();
        parameters.put("name", "Item1");

        data = queryExecutor.fetch(getQuery(), this::toItem, parameters);

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    private final String getQuery() {
        return "MATCH (i:Item) WHERE i.name = $name RETURN i.name AS name, i.description AS description";
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
            new ImmutableItemStats("", 0l, 0, Collections.emptyList()),
            description, tags);
    }

}
