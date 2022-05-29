
package com.bernardomg.darksouls.explorer.item.ammunition.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.PersistentAmmunition;

public interface AmmunitionRepository
        extends PagingAndSortingRepository<PersistentAmmunition, Long> {

}
