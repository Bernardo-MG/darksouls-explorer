
package com.bernardomg.darksouls.explorer.item.weapon.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.darksouls.explorer.item.weapon.domain.path.PersistentWeaponLevel;

public interface WeaponLevelRepository
        extends JpaRepository<PersistentWeaponLevel, Long> {

    @Query("SELECT l FROM WeaponLevel l WHERE l.name = :name ORDER BY l.path ASC, l.level ASC")
    public Collection<PersistentWeaponLevel>
            findAllByName(@Param("name") final String name);

}
