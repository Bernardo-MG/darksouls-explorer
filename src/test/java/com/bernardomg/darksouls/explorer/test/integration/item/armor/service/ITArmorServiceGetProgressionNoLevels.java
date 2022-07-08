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

package com.bernardomg.darksouls.explorer.test.integration.item.armor.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.repository.ArmorRepository;
import com.bernardomg.darksouls.explorer.item.armor.service.ArmorService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Reading armor progression with no levels")
@Sql({ "/db/queries/armor/single.sql" })
public class ITArmorServiceGetProgressionNoLevels {

    @Autowired
    private ArmorRepository repository;

    @Autowired
    private ArmorService    service;

    /**
     * Default constructor.
     */
    public ITArmorServiceGetProgressionNoLevels() {
        super();
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

    @Test
    @DisplayName("Returns no level progression")
    public void testGetProgression_NotData() {
        final Optional<ArmorProgression> data;
        final Long                       id;

        id = getId();

        data = service.getProgression(id);

        Assertions.assertFalse(data.isPresent());
    }

}
