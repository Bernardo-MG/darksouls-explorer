
package com.bernardomg.darksouls.explorer.item.ammunition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.PersistentAmmunition;

public interface AmmunitionRepository
        extends JpaRepository<PersistentAmmunition, Long> {

}
