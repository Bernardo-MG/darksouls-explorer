
package com.bernardomg.darksouls.explorer.item.spell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.spell.domain.PersistentSpell;

public interface SpellRepository extends JpaRepository<PersistentSpell, Long> {

}
