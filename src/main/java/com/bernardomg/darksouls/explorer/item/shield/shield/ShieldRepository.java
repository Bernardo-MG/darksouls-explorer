
package com.bernardomg.darksouls.explorer.item.shield.shield;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentSpell;

public interface ShieldRepository
        extends PagingAndSortingRepository<PersistentSpell, Long> {

}
