
package com.bernardomg.darksouls.explorer.item.talisman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.talisman.domain.PersistentTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.TalismanSummary;

public interface TalismanRepository
        extends JpaRepository<PersistentTalisman, Long> {

    @Query("SELECT t FROM Talisman t")
    public Page<TalismanSummary> findAllSummaries(final Pageable pageable);

}
