
package com.bernardomg.darksouls.explorer.item.talisman.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.talisman.domain.PersistentTalisman;

public interface TalismanRepository
        extends PagingAndSortingRepository<PersistentTalisman, Long> {

}
