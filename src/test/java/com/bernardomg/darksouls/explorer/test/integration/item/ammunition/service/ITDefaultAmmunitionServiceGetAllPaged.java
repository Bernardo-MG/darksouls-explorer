/**
 * Copyright 2021-2022 the original author or authors
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

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.ammunition.service.DefaultAmmunitionService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;
import com.bernardomg.darksouls.explorer.test.configuration.db.ContainerFactory;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@IntegrationTest
@DisplayName("Reading all the catalysts paginated")
@Sql({ "/db/queries/ammunition/multiple.sql" })
public class ITDefaultAmmunitionServiceGetAllPaged {

    @Container
    private static final MySQLContainer<?> mysqlContainer = ContainerFactory.getMysqlContainer();

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
    }

    @Autowired
    private DefaultAmmunitionService service;

    /**
     * Default constructor.
     */
    public ITDefaultAmmunitionServiceGetAllPaged() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Instance() {
        final Iterable<Summary> data;

        data = service.getAll(Pagination.of(0, 1), Sort.disabled());

        Assertions.assertInstanceOf(PageIterable.class, data);
    }

    @Test
    @DisplayName("Applies pagination size")
    public void testGetAll_SingleResult() {
        final Iterable<Summary> data;

        data = service.getAll(Pagination.of(0, 1), Sort.disabled());

        Assertions.assertEquals(1, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Pagination with size 0 returns default size")
    public void testGetAll_Size0_Size() {
        final Iterable<Summary> data;

        data = service.getAll(Pagination.of(0, 0), Sort.disabled());

        Assertions.assertEquals(5, IterableUtils.size(data));
    }

    @Test
    @DisplayName("Pagination with size 0 contains all the data")
    public void testGetAll_Size0_Values() {
        final PageIterable<Summary> data;

        data = service.getAll(Pagination.of(0, 0), Sort.disabled());

        Assertions.assertEquals(5, data.getElementsInPage());
        Assertions.assertEquals(0, data.getPageNumber());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(1, data.getTotalPages());
    }

    @Test
    @DisplayName("When unpaged returns all the data")
    public void testGetAll_Unpaged() {
        final Iterable<Summary> data;

        data = service.getAll(Pagination.disabled(), Sort.disabled());

        Assertions.assertEquals(5, IterableUtils.size(data));
    }

    @Test
    @DisplayName("The returned page contains all the data")
    public void testGetAll_Values() {
        final PageIterable<Summary> data;

        data = service.getAll(Pagination.of(0, 1), Sort.disabled());

        Assertions.assertEquals(1, data.getElementsInPage());
        Assertions.assertEquals(0, data.getPageNumber());
        Assertions.assertEquals(1, data.getSize());
        Assertions.assertEquals(5, data.getTotalElements());
        Assertions.assertEquals(5, data.getTotalPages());
    }

}
