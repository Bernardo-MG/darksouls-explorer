
package com.bernardomg.darksouls.explorer.item.weapon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.weapon.domain.PersistentWeapon;

public interface WeaponRepository extends JpaRepository<PersistentWeapon, Long> {

    @Query("SELECT w FROM Weapon w")
    public Page<Summary> findAllSummaries(final Pageable pageable);

    @Query("SELECT w FROM Weapon w WHERE w.type = :type")
    public Page<Summary> findAllSummaries(@Param("type") final String type, final Pageable pageable);

}
