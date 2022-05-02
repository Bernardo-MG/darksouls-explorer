
package com.bernardomg.darksouls.explorer.item.weapon.domain;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableWeapon implements Weapon {

    @NonNull
    private final Iterable<String>   description;

    @NonNull
    private final Long               id;

    @NonNull
    private final String             name;

    @NonNull
    private final WeaponRequirements requirements;

    @NonNull
    private final ItemStats          stats;

    public ImmutableWeapon(@NonNull final Long id, @NonNull final String name,
            @NonNull final WeaponRequirements requirements,
            @NonNull final ItemStats stats,
            @NonNull final Iterable<String> description) {
        super();

        this.id = id;
        this.name = name;
        this.requirements = requirements;
        this.stats = stats;
        this.description = description;
    }

}
