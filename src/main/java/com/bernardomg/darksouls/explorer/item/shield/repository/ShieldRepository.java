
package com.bernardomg.darksouls.explorer.item.shield.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentShield;
import com.bernardomg.darksouls.explorer.item.shield.domain.ShieldSummary;

public interface ShieldRepository
        extends JpaRepository<PersistentShield, Long> {

    @Query("SELECT s FROM Shield s")
    public Page<ShieldSummary> findAllSummaries(final Pageable pageable);

}
