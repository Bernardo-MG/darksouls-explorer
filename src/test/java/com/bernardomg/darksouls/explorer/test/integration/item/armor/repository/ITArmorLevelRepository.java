
package com.bernardomg.darksouls.explorer.test.integration.item.armor.repository;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.repository.ArmorLevelRepository;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Weapon levels repository")
@Sql({ "/db/queries/armor/armor_2_levels.sql" })
public class ITArmorLevelRepository {

    @Autowired
    private ArmorLevelRepository levelRepository;

    public ITArmorLevelRepository() {
        super();
    }

    @Test
    @DisplayName("Returns all the levels")
    public void testFindAllForNames_Count() {
        final Collection<PersistentArmorLevel> levels;

        levels = levelRepository.findAllByName("Chain Armor");

        Assertions.assertEquals(2, IterableUtils.size(levels));
    }

    @Test
    @DisplayName("Returns levels in the correct order")
    public void testFindAllForNames_Order() {
        final Iterator<PersistentArmorLevel> levels;
        PersistentArmorLevel level;

        levels = levelRepository.findAllByName("Chain Armor")
            .iterator();

        level = levels.next();

        Assertions.assertEquals(0, level.getLevel());

        level = levels.next();

        Assertions.assertEquals(1, level.getLevel());
    }

}
