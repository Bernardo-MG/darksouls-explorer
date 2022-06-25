
package com.bernardomg.darksouls.explorer.test.integration.item.weapon.repository;

import java.util.Arrays;
import java.util.Collection;

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
    public void testFindAllForNames() {
        final Iterable<String> names;
        final Collection<PersistentWeaponLevel> levels;

        names = Arrays.asList("Sword");

        levels = levelRepository.findAllForNames(names);

        Assertions.assertEquals(5, IterableUtils.size(levels));
    }

}
