
package com.bernardomg.darksouls.explorer.item.key.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.key.domain.PersistentKeyItem;

public interface KeyItemRepository
        extends JpaRepository<PersistentKeyItem, Long> {

}
