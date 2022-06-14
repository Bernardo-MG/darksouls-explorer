
package com.bernardomg.darksouls.explorer.item.catalyst.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.CatalystSummary;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.PersistentCatalyst;

public interface CatalystRepository
        extends JpaRepository<PersistentCatalyst, Long> {

    @Query("SELECT c FROM Catalyst c")
    public Page<CatalystSummary> findAllSummaries(final Pageable pageable);

}
