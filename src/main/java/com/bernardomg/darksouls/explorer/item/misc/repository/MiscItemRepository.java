
package com.bernardomg.darksouls.explorer.item.misc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.misc.domain.MiscItem;
import com.bernardomg.darksouls.explorer.item.misc.domain.PersistentMiscItem;

public interface MiscItemRepository
        extends JpaRepository<PersistentMiscItem, Long> {

    @Query("SELECT m FROM MiscItem m")
    public Page<MiscItem> findAllSummaries(final Pageable pageable);

}
