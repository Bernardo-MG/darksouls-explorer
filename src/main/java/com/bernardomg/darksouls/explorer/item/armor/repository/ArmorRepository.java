
package com.bernardomg.darksouls.explorer.item.armor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmor;

public interface ArmorRepository extends JpaRepository<PersistentArmor, Long> {

}
