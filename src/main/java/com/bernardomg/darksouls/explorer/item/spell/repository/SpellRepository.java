
package com.bernardomg.darksouls.explorer.item.spell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bernardomg.darksouls.explorer.item.spell.domain.PersistentSpell;
import com.bernardomg.darksouls.explorer.item.spell.domain.SpellSummary;

public interface SpellRepository extends JpaRepository<PersistentSpell, Long> {

    @Query("SELECT s FROM Spell s")
    public Page<SpellSummary> findAllSummaries(final Pageable pageable);

}
