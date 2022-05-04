
package com.bernardomg.darksouls.explorer.item.armor.query;

import java.util.Arrays;
import java.util.Map;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmor;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;
import com.bernardomg.darksouls.explorer.item.persistence.GenericQuery;

public final class ArmorsQuery extends GenericQuery<Armor> {

    public ArmorsQuery() {
        super("Armor",
            Arrays.asList("id", "name", "description", "durability", "weight"));
    }

    @Override
    public final Armor getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final ItemStats stats;

        id = (Long) record.getOrDefault("id", -1l);
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));

        stats = getStats(record);

        return new ImmutableArmor(id, name, stats, description);
    }

    private final ItemStats getStats(final Map<String, Object> record) {
        final Long weight;
        final Integer durability;

        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();

        return new ImmutableItemStats(weight, durability);
    }

}
