
package com.bernardomg.darksouls.explorer.item.itemdata.service;

import java.util.Optional;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponProgression;

public interface ItemProgressionService {

    public Optional<ArmorProgression> getArmorProgression(final Long id);

    public Optional<WeaponProgression> getWeaponProgression(final Long id);

}
