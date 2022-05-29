
package com.bernardomg.darksouls.explorer.item.misc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.misc.domain.PersistentMiscItem;

public interface MiscItemRepository
        extends PagingAndSortingRepository<PersistentMiscItem, Long> {

}
