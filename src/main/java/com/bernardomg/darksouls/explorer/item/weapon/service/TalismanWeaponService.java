
package com.bernardomg.darksouls.explorer.item.weapon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponLevelRepository;
import com.bernardomg.darksouls.explorer.item.weapon.repository.WeaponRepository;
import com.bernardomg.persistence.executor.QueryExecutor;

@Service("TalismanWeaponService")
public final class TalismanWeaponService extends AbstractWeaponService {

    @Autowired
    public TalismanWeaponService(final WeaponRepository repo,
            final WeaponLevelRepository levelRepo,
            final QueryExecutor queryExec) {
        super(repo, levelRepo, queryExec, "Talisman");
    }

}
