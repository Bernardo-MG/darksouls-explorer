
package com.bernardomg.darksouls.explorer.item.weapon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.weapon.domain.PersistentWeapon;

public interface WeaponRepository
        extends PagingAndSortingRepository<PersistentWeapon, Long> {

}
