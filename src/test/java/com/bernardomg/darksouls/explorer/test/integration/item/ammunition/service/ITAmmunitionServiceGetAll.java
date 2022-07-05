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

package com.bernardomg.darksouls.explorer.test.integration.item.ammunition.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.ammunition.service.AmmunitionService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.pagination.model.DisabledPagination;
import com.bernardomg.pagination.model.DisabledSort;

@IntegrationTest
@DisplayName("Reading all the catalysts")
@Sql({ "/db/queries/ammunition/single.sql" })
public class ITAmmunitionServiceGetAll {

    @Container
    private static final MySQLContainer<?> mysqlContainer = ContainerFactory.getMysqlContainer();

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }

    @Autowired
    private AmmunitionService service;

    /**
     * Default constructor.
     */
    public ITAmmunitionServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the data")
    public void testGetAll_Count() {
        final Iterable<? extends Ammunition> data;

        data = service.getAll(new DisabledPagination(), new DisabledSort());

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Returns the correct data")
    public void testGetAll_Data() {
        final Ammunition data;

        data = service.getAll(new DisabledPagination(), new DisabledSort())
            .iterator()
            .next();

        Assertions.assertEquals("Ammunition", data.getName());
        Assertions.assertEquals("Description", data.getDescription());
        Assertions.assertEquals("Type", data.getType());
    }

}
