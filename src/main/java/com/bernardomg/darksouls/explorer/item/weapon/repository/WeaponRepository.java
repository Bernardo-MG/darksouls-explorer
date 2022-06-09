
package com.bernardomg.darksouls.explorer.item.weapon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.weapon.domain.PersistentWeapon;

public interface WeaponRepository
        extends JpaRepository<PersistentWeapon, Long> {

}
