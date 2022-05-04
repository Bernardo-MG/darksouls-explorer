
package com.bernardomg.darksouls.explorer.item.spell.query;

import java.util.Arrays;
import java.util.Map;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;
import com.bernardomg.darksouls.explorer.item.persistence.GenericQuery;
import com.bernardomg.darksouls.explorer.item.spell.domain.ImmutableSpell;
import com.bernardomg.darksouls.explorer.item.spell.domain.Spell;

public final class SpellsQuery extends GenericQuery<Spell> {

    public SpellsQuery() {
        super("Spell", Arrays.asList("id", "name", "description"));
    }

    @Override
    public final Spell getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final ItemStats itemStats;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));
        itemStats = getStats(record);

        return new ImmutableSpell(id, name, itemStats, description);
    }

    private final ItemStats getStats(final Map<String, Object> record) {
        final Long weight;
        final Integer durability;

        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();

        return new ImmutableItemStats(weight, durability);
    }

}
