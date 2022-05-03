
package com.bernardomg.darksouls.explorer.item.armor.query;

import java.util.Arrays;
import java.util.Map;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingWithoutWhere;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmor;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ImmutableItemStats;
import com.bernardomg.darksouls.explorer.item.itemdata.domain.ItemStats;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ArmorsQuery implements Query<Armor> {

    public ArmorsQuery() {
        super();
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

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final Node item;
        final Expression nodeName;
        final Expression nodeId;
        final OngoingReadingWithoutWhere builder;
        final Condition nameCondition;
        final Condition idCondition;
        final String name;

        // Root
        item = Cypher.node("Armor")
            .named("s");
        nodeName = item.property("name");
        nodeId = Functions.id(item);

        builder = Cypher.match(item);

        if (params.containsKey("name")) {
            name = String.valueOf(params.get("name"));
            nameCondition = nodeName.matches("(?i).*" + name + ".*");
            builder.where(nameCondition);
        }

        if (params.containsKey("id")) {
            idCondition = nodeId.eq(Cypher.parameter("id"));
            // TODO: combine
            builder.where(idCondition);
        }

        return builder
            .returning(nodeId.as("id"), nodeName.as("name"),
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
                    .as("weight"))
            .build()
            .getCypher();
    }

    private final ItemStats getStats(final Map<String, Object> record) {
        final Long weight;
        final Integer durability;

        weight = (Long) record.getOrDefault("weight", -1l);
        durability = ((Long) record.getOrDefault("durability", -1l)).intValue();

        return new ImmutableItemStats(weight, durability);
    }

}
