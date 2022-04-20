
package com.bernardomg.darksouls.explorer.item.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingWithoutWhere;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemRequirements;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemRequirements;
import com.bernardomg.darksouls.explorer.item.domain.ItemStats;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class AllItemsQuery implements Query<Item> {

    private final String           name;

    private final Iterable<String> tags;

    public AllItemsQuery(final String name, final Iterable<String> tags) {
        super();

        this.name = name;
        this.tags = tags;
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
        itemStats = getItemStats(record);

        return new ImmutableItem(id, name, itemRequirements, itemStats,
            description, tagsSorted);
    }

    @Override
    public final String getStatement() {
        final Node item;
        final Expression nodeName;
        final OngoingReadingWithoutWhere ongoingBuilder;
        final String[] additionalLabels;
        final Condition nameCondition;

        additionalLabels = StreamSupport.stream(tags.spliterator(), false)
            .toArray(String[]::new);
        item = Cypher.node("Item", additionalLabels)
            .named("s");
        nodeName = item.property("name");

        ongoingBuilder = Cypher.match(item);

        if (!name.isBlank()) {
            nameCondition = nodeName.matches("(?i).*" + name + ".*");
            ongoingBuilder.where(nameCondition);
        }

        return ongoingBuilder.returning(Functions.id(item)
            .as("id"), nodeName.as("name"),
            item.property("description")
                .as("description"),
            item.property("dexterity")
                .as("dexterity"),
            item.property("faith")
                .as("faith"),
            item.property("strength")
                .as("strength"),
            item.property("intelligence")
                .as("intelligence"),
            item.property("durability")
                .as("durability"),
            item.property("weight")
                .as("weight"),
            Functions.labels(item)
                .as("labels"))
            .build()
            .getCypher();
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
        final String type;
        final Long weight;
        final Integer durability;
        final Iterable<String> attacks;

        type = (String) record.getOrDefault("type", "");
        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();
        attacks = new ArrayList<>();

        return new ImmutableItemStats(type, weight, durability, attacks);
    }

}
