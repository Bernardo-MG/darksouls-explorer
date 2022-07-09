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

import java.util.Iterator;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.repository.ArmorRepository;
import com.bernardomg.darksouls.explorer.item.armor.service.ArmorService;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Reading armor progression")
@Sql({ "/db/queries/armor/single.sql", "/db/queries/armor/armor_2_levels.sql" })
public class ITArmorServiceGetProgression {

    @Autowired
    private ArmorRepository repository;

    @Autowired
    private ArmorService    service;

    /**
     * Default constructor.
     */
    public ITArmorServiceGetProgression() {
        super();
    }

    @Test
    @DisplayName("Returns the levels in order")
    public void testGetProgression_LevelsOrder() {
        final ArmorProgression               data;
        final Iterator<? extends ArmorLevel> levels;
        final Long                           id;
        ArmorLevel                           level;

        id = getId();

        data = service.getProgression(id)
            .get();

        levels = data.getLevels()
            .iterator();

        level = levels.next();
        Assertions.assertEquals(0, level.getLevel());

        level = levels.next();
        Assertions.assertEquals(1, level.getLevel());
    }

    @Test
    @DisplayName("Returns the levels protections")
    public void testGetProgression_LevelsProtection() {
        final ArmorProgression               data;
        final Iterator<? extends ArmorLevel> levels;
        final Long                           id;
        ArmorLevel                           level;

        id = getId();

        data = service.getProgression(id)
            .get();

        levels = data.getLevels()
            .iterator();

        level = levels.next();
        Assertions.assertEquals(10, level.getRegularProtection());
        Assertions.assertEquals(11, level.getStrikeProtection());
        Assertions.assertEquals(12, level.getSlashProtection());
        Assertions.assertEquals(13, level.getThrustProtection());
        Assertions.assertEquals(14, level.getMagicProtection());
        Assertions.assertEquals(15, level.getFireProtection());
        Assertions.assertEquals(16, level.getLightningProtection());
        Assertions.assertEquals(17, level.getBleedProtection());
        Assertions.assertEquals(18, level.getPoisonProtection());
        Assertions.assertEquals(19, level.getCurseProtection());

        level = levels.next();
        Assertions.assertEquals(20, level.getRegularProtection());
        Assertions.assertEquals(21, level.getStrikeProtection());
        Assertions.assertEquals(22, level.getSlashProtection());
        Assertions.assertEquals(23, level.getThrustProtection());
        Assertions.assertEquals(24, level.getMagicProtection());
        Assertions.assertEquals(25, level.getFireProtection());
        Assertions.assertEquals(26, level.getLightningProtection());
        Assertions.assertEquals(27, level.getBleedProtection());
        Assertions.assertEquals(28, level.getPoisonProtection());
        Assertions.assertEquals(29, level.getCurseProtection());
    }

    @Test
    @DisplayName("Returns no level progression when asking for a not existing weapon")
    public void testGetProgression_NotExisting() {
        final Optional<ArmorProgression> data;

        data = service.getProgression(-1l);

        Assertions.assertFalse(data.isPresent());
    }

    @Test
    @DisplayName("Returns the expected structure")
    public void testGetProgression_Structure() {
        final ArmorProgression data;
        final Long             id;

        id = getId();

        data = service.getProgression(id)
            .get();

        Assertions.assertEquals("Chain Armor", data.getArmor());
        Assertions.assertEquals(2, IterableUtils.size(data.getLevels()));
    }

    private final Long getId() {
        return repository.findAll()
            .iterator()
            .next()
            .getId();
    }

}
