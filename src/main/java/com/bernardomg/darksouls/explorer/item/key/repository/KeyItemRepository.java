
package com.bernardomg.darksouls.explorer.item.key.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.key.domain.KeyItem;
import com.bernardomg.darksouls.explorer.item.key.domain.PersistentKeyItem;

public interface KeyItemRepository
        extends JpaRepository<PersistentKeyItem, Long> {

    @Query("SELECT k FROM KeyItem k")
    public Page<KeyItem> findAllSummaries(final Pageable pageable);

}
