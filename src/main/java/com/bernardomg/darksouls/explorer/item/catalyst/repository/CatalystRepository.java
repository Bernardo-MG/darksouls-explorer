
package com.bernardomg.darksouls.explorer.item.catalyst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.PersistentCatalyst;

public interface CatalystRepository
        extends JpaRepository<PersistentCatalyst, Long> {

}
