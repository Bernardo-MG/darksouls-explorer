
package com.bernardomg.darksouls.explorer.item.weapon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.persistence.executor.QueryExecutor;

@Service("BasicWeaponService")
public final class DefaultWeaponService extends AbstractWeaponService {

    @Autowired
    public DefaultWeaponService(final WeaponRepository repo,
            final WeaponLevelRepository levelRepo,
            final QueryExecutor queryExec) {
        super(repo, levelRepo, queryExec, "Weapon");
    }

}
