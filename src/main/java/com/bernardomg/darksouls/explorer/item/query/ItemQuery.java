
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemRequirements;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemRequirements;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ItemQuery implements Query<Item> {

    public ItemQuery() {
        super();
    }

    @Override
    public final Item getOutput(final Iterable<Map<String, Object>> record) {
        return StreamSupport.stream(record.spliterator(), false)
            .map(this::toItem)
            .findFirst()
            .orElse(null);
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
        + "   labels(i) AS labels" + System.lineSeparator();
        // @formatter:on;

        return query;
    }

    public final Item toItem(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final Iterable<String> tags;
        final Iterable<String> tagsSorted;
        final ItemRequirements itemRequirements;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));
        tags = (Iterable<String>) record.getOrDefault("labels",
            Collections.emptyList());
        tagsSorted = StreamSupport.stream(tags.spliterator(), false)
            .sorted()
            .collect(Collectors.toList());
        itemRequirements = getItemRequirements(record);

        return new ImmutableItem(id, name, itemRequirements, description,
            tagsSorted);
    }

    private final ItemRequirements
            getItemRequirements(final Map<String, Object> record) {
        final Integer dexterity;
        final Integer faith;
        final Integer strength;
        final Integer intelligence;

        dexterity = (Integer) record.getOrDefault("dexterity", -1);
        faith = (Integer) record.getOrDefault("faith", -1);
        strength = (Integer) record.getOrDefault("strength", -1);
        intelligence = (Integer) record.getOrDefault("intelligence", -1);

        return new ImmutableItemRequirements(dexterity, faith, strength,
            intelligence);
    }

}
