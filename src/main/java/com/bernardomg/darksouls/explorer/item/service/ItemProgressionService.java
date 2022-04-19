
package com.bernardomg.darksouls.explorer.item.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;

public interface ItemProgressionService {

    public Optional<ArmorProgression> getArmorProgression(final Long id);

    public Optional<WeaponProgression> getWeaponProgression(final Long id);

}
