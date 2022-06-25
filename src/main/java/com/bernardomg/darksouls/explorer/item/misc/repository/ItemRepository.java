
package com.bernardomg.darksouls.explorer.item.misc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.darksouls.explorer.item.misc.domain.Item;
import com.bernardomg.darksouls.explorer.item.misc.domain.PersistentItem;

public interface ItemRepository extends JpaRepository<PersistentItem, Long> {

    @Query("SELECT i FROM Item i")
    public Page<Item> findAllSummaries(final Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.type = :type")
    public Page<Item> findAllSummaries(@Param("type") final String type,
            final Pageable pageable);

}
