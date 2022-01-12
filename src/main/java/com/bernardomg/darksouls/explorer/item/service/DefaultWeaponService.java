
package com.bernardomg.darksouls.explorer.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.query.WeaponQueries;

@Service
public final class DefaultWeaponService implements WeaponService {

    private final WeaponQueries queries;

    @Autowired
    public DefaultWeaponService(final WeaponQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final WeaponProgression getWeaponLevels(final String weapon) {
        return queries.findWeaponSources(weapon);
    }

}
