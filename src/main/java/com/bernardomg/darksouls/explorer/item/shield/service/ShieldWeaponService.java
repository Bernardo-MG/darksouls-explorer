
package com.bernardomg.darksouls.explorer.item.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.darksouls.explorer.item.weapon.service.AbstractWeaponService;
import com.bernardomg.persistence.executor.QueryExecutor;

@Service("ShieldWeaponService")
public final class ShieldWeaponService extends AbstractWeaponService {

    @Autowired
    public ShieldWeaponService(final WeaponRepository repo,
            final WeaponLevelRepository levelRepo,
            final QueryExecutor queryExec) {
        super(repo, levelRepo, queryExec, "Shield");
    }

}
