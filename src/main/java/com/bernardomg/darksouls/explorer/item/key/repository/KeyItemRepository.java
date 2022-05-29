
package com.bernardomg.darksouls.explorer.item.key.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.key.domain.PersistentKeyItem;

public interface KeyItemRepository
        extends PagingAndSortingRepository<PersistentKeyItem, Long> {

}
