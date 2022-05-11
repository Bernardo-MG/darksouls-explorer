
package com.bernardomg.darksouls.explorer.item.weapon.query;

import java.util.Arrays;
import java.util.Map;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;
import com.bernardomg.darksouls.explorer.item.persistence.GenericQuery;
import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponRequirements;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponRequirements;
import com.bernardomg.darksouls.explorer.persistence.utils.Maps;

public final class WeaponsQuery extends GenericQuery<Weapon> {

    public WeaponsQuery() {
        super("Weapon", Arrays.asList("id", "name", "description", "dexterity",
            "faith", "strength", "intelligence", "durability", "weight"));
    }

    @Override
    public final Weapon getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final WeaponRequirements requirements;
        final ItemStats stats;

        id = Maps.getOrDefault(record, "id", -1l);
        name = Maps.getOrDefault(record, "name", "");
        description = Arrays.asList(Maps.getOrDefault(record, "description", "")
            .split("\\|"));
        requirements = getRequirements(record);
        stats = getStats(record);

        return new ImmutableWeapon(id, name, requirements, stats, description);
    }

    private final WeaponRequirements
            getRequirements(final Map<String, Object> record) {
        final Integer dexterity;
        final Integer faith;
        final Integer strength;
        final Integer intelligence;

        dexterity = Maps.getOrDefault(record, "dexterity", -1l)
            .intValue();
        faith = Maps.getOrDefault(record, "faith", -1l)
            .intValue();
        strength = Maps.getOrDefault(record, "strength", -1l)
            .intValue();
        intelligence = Maps.getOrDefault(record, "intelligence", -1l)
            .intValue();

        return new ImmutableWeaponRequirements(dexterity, faith, strength,
            intelligence);
    }

    private final ItemStats getStats(final Map<String, Object> record) {
        final Long weight;
        final Integer durability;

        weight = Maps.getOrDefault(record, "weight", -1l);
        durability = Maps.getOrDefault(record, "durability", -1l)
            .intValue();

        return new ImmutableItemStats(weight, durability);
    }

}
