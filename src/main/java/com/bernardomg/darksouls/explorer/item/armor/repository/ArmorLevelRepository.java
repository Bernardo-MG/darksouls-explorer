
package com.bernardomg.darksouls.explorer.item.armor.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.darksouls.explorer.item.armor.domain.PersistentArmorLevel;

public interface ArmorLevelRepository extends JpaRepository<PersistentArmorLevel, Long> {

    @Query("SELECT l FROM ArmorLevel l WHERE l.name = :name ORDER BY l.level ASC")
    public Collection<PersistentArmorLevel> findAllByName(@Param("name") final String name);

}
