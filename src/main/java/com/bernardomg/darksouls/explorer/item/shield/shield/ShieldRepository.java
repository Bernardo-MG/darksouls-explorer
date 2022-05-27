
package com.bernardomg.darksouls.explorer.item.shield.shield;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentShield;

public interface ShieldRepository
        extends PagingAndSortingRepository<PersistentShield, Long> {

}
