
package com.bernardomg.darksouls.explorer.item.shield.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.shield.domain.PersistentShield;

public interface ShieldRepository
        extends JpaRepository<PersistentShield, Long> {

}
