
package com.bernardomg.darksouls.explorer.item.misc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.misc.domain.PersistentMiscItem;

public interface MiscItemRepository
        extends JpaRepository<PersistentMiscItem, Long> {

}
