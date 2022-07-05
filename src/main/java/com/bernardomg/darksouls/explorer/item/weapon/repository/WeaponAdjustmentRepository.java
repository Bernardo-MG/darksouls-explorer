
package com.bernardomg.darksouls.explorer.item.weapon.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.PersistentWeaponAdjustment;

public interface WeaponAdjustmentRepository
        extends JpaRepository<PersistentWeaponAdjustment, Long> {

    @Query("SELECT a FROM WeaponAdjustment a WHERE a.name = :name ORDER BY a.faith ASC, a.intelligence ASC")
    public Collection<PersistentWeaponAdjustment>
            findAllByName(@Param("name") final String name);

}
