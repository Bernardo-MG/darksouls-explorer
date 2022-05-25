
package com.bernardomg.darksouls.explorer.item.catalyst.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.PersistentCatalyst;

public interface CatalystRepository
        extends PagingAndSortingRepository<PersistentCatalyst, Long> {

}
