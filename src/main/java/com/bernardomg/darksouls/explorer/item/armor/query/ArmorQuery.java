
package com.bernardomg.darksouls.explorer.item.armor.query;

import java.util.Arrays;
import java.util.Map;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmor;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ArmorQuery implements Query<Armor> {

    public ArmorQuery() {
        super();
    }

    @Override
    public final Armor getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final ItemStats itemStats;

        id = (Long) record.getOrDefault("id", -1l);
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));

        itemStats = getStats(record);

        return new ImmutableArmor(id, name, itemStats, description);
    }

    @Override
    public final String getStatement() {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "   (i:Armor) " + System.lineSeparator()
        + "WHERE" + System.lineSeparator()
        + "   id(i) = $id" + System.lineSeparator()
        + "RETURN" + System.lineSeparator()
        + "   id(i) AS id," + System.lineSeparator()
        + "   i.name AS name," + System.lineSeparator()
        + "   i.description AS description," + System.lineSeparator()
        + "   i.dexterity AS dexterity," + System.lineSeparator()
        + "   i.faith AS faith," + System.lineSeparator()
        + "   i.strength AS strength," + System.lineSeparator()
        + "   i.intelligence AS intelligence," + System.lineSeparator()
        + "   i.durability AS durability," + System.lineSeparator()
        + "   i.weight AS weight," + System.lineSeparator()
        + "   labels(i) AS labels" + System.lineSeparator();
        // @formatter:on;

        return query;
    }

    private final ItemStats getStats(final Map<String, Object> record) {
        final Long weight;
        final Integer durability;

        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();

        return new ImmutableItemStats(weight, durability);
    }

}
