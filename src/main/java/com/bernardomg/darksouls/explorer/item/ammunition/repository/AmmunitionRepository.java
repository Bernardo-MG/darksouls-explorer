
package com.bernardomg.darksouls.explorer.item.ammunition.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.ammunition.domain.PersistentAmmunition;

public interface AmmunitionRepository
        extends JpaRepository<PersistentAmmunition, Long> {

    @Query("SELECT a FROM Ammunition a")
    public Page<Ammunition> findAllSummaries(final Pageable pageable);

}
