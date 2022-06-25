
package com.bernardomg.darksouls.explorer.item.spell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.darksouls.explorer.item.spell.domain.PersistentSpell;
import com.bernardomg.darksouls.explorer.item.spell.domain.SpellSummary;

public interface SpellRepository extends JpaRepository<PersistentSpell, Long> {

    @Query("SELECT s FROM Spell s")
    public Page<SpellSummary> findAllSummaries(final Pageable pageable);

    @Query("SELECT s FROM Spell s WHERE s.school = :school")
    public Page<SpellSummary> findAllSummaries(
            @Param("school") final String school, final Pageable pageable);

}
