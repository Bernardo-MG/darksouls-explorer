
package com.bernardomg.darksouls.explorer.item.catalyst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.darksouls.explorer.item.weapon.service.AbstractWeaponService;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;

@Service("CatalystWeaponService")
public final class CatalystWeaponService extends AbstractWeaponService {

    @Autowired
    public CatalystWeaponService(final WeaponRepository repo,
            final WeaponLevelRepository levelRepo,
            final QueryExecutor queryExec) {
        super(repo, levelRepo, queryExec, "Catalyst");
    }

}
