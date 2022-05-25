
package com.bernardomg.darksouls.explorer.item.spell.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bernardomg.darksouls.explorer.item.spell.domain.PersistentSpell;

public interface SpellRepository
        extends PagingAndSortingRepository<PersistentSpell, Long> {

}
