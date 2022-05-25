
package com.bernardomg.darksouls.explorer.item.armor.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmor;

public interface ArmorRepository
        extends PagingAndSortingRepository<PersistentArmor, Long> {

}
