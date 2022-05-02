
package com.bernardomg.darksouls.explorer.item.itemdata.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemRequirements;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.Item;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemRequirements;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ItemQuery implements Query<Item> {

    public ItemQuery() {
        super();
    }

    @Override
    public final Item getOutput(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final Iterable<String> tags;
        final Iterable<String> tagsSorted;
        final ItemRequirements itemRequirements;
        final ItemStats itemStats;

        id = (Long) record.getOrDefault("id", -1l);
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));
        tags = (Iterable<String>) record.getOrDefault("labels",
            Collections.emptyList());
        tagsSorted = StreamSupport.stream(tags.spliterator(), false)
            .sorted()
            .collect(Collectors.toList());

        itemRequirements = getItemRequirements(record);
        itemStats = getItemStats(record);

        return new ImmutableItem(id, name, itemRequirements, itemStats,
            description, tagsSorted);
    }

    @Override
    public final String getStatement() {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "   (i:Item) " + System.lineSeparator()
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

    private final ItemRequirements
            getItemRequirements(final Map<String, Object> record) {
        final Integer dexterity;
        final Integer faith;
        final Integer strength;
        final Integer intelligence;

        dexterity = ((Long) record.getOrDefault("dexterity", -1l)).intValue();
        faith = ((Long) record.getOrDefault("faith", -1l)).intValue();
        strength = ((Long) record.getOrDefault("strength", -1l)).intValue();
        intelligence = ((Long) record.getOrDefault("intelligence", -1l))
            .intValue();

        return new ImmutableItemRequirements(dexterity, faith, strength,
            intelligence);
    }

    private final ItemStats getItemStats(final Map<String, Object> record) {
        final Long weight;
        final Integer durability;

        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();

        return new ImmutableItemStats(weight, durability);
    }

}
