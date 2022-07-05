
package com.bernardomg.darksouls.explorer.test.integration.item.weapon.repository;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.darksouls.explorer.item.weapon.domain.path.PersistentWeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Weapon levels repository")
@Sql({ "/db/queries/weapon/physical_5_levels.sql" })
public class ITWeaponLevelRepository {

    @Autowired
    private WeaponLevelRepository levelRepository;

    public ITWeaponLevelRepository() {
        super();
    }

    @Test
    @DisplayName("Returns all the levels")
    public void testFindAllForNames() {
        final Collection<PersistentWeaponLevel> levels;

        levels = levelRepository.findAllByName("Sword");

        Assertions.assertEquals(5, IterableUtils.size(levels));
    }

    @Test
    @DisplayName("Returns levels in the correct order")
    public void testFindAllForNames_Order() {
        final Iterator<PersistentWeaponLevel> levels;
        PersistentWeaponLevel                 level;

        levels = levelRepository.findAllByName("Sword")
                .iterator();

        level = levels.next();

        Assertions.assertEquals(0, level.getLevel());

        level = levels.next();

        Assertions.assertEquals(1, level.getLevel());
    }

}
