
package com.bernardomg.darksouls.explorer.item.talisman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.talisman.domain.PersistentTalisman;

public interface TalismanRepository
        extends JpaRepository<PersistentTalisman, Long> {

}
