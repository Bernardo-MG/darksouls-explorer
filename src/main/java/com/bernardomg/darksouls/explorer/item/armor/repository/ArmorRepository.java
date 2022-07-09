
package com.bernardomg.darksouls.explorer.item.armor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorSummary;
import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmor;

public interface ArmorRepository extends JpaRepository<PersistentArmor, Long> {

    @Query("SELECT a FROM Armor a")
    public Page<ArmorSummary> findAllSummaries(final Pageable pageable);

}
