
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.item.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableArmorLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ArmorProgressionQuery
        implements Query<List<ArmorProgression>> {

    public ArmorProgressionQuery() {
        super();
    }

    @Override
    public final List<ArmorProgression>
            getOutput(final Iterable<Map<String, Object>> record) {
        return Arrays.asList(toArmorProgression(record));
    }

    @Override
    public final String getStatement() {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "   (a:Armor)-[HAS_LEVEL]->(l:Level) " + System.lineSeparator()
        + "WHERE" + System.lineSeparator()
        + "  id(a) = $id" + System.lineSeparator()
        + "RETURN" + System.lineSeparator()
        + "   l.armor AS armor," + System.lineSeparator()
        + "   l.level AS level," + System.lineSeparator()
        + "   l.regularProtection AS regularProtection," + System.lineSeparator()
        + "   l.strikeProtection AS strikeProtection," + System.lineSeparator()
        + "   l.slashProtection AS slashProtection," + System.lineSeparator()
        + "   l.thrustProtection AS thrustProtection," + System.lineSeparator()
        + "   l.magicProtection AS magicProtection," + System.lineSeparator()
        + "   l.fireProtection AS fireProtection," + System.lineSeparator()
        + "   l.lightningProtection AS lightningProtection," + System.lineSeparator()
        + "   l.bleedProtection AS bleedProtection," + System.lineSeparator()
        + "   l.poisonProtection AS poisonProtection," + System.lineSeparator()
        + "   l.curseProtection AS curseProtection" + System.lineSeparator()
        + "ORDER BY" + System.lineSeparator()
        + "   armor ASC," + System.lineSeparator()
        + "   level ASC";
        // @formatter:on;

        return query;
    }

    private final ArmorLevel toArmorLevel(final Map<String, Object> record) {
        return new ImmutableArmorLevel(
            ((Number) record.getOrDefault("level", 0)).intValue(),
            ((Number) record.getOrDefault("regularProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("strikeProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("slashProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("thrustProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("magicProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("fireProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("lightningProtection", 0))
                .floatValue(),
            ((Number) record.getOrDefault("bleedProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("poisonProtection", 0)).floatValue(),
            ((Number) record.getOrDefault("curseProtection", 0)).floatValue());
    }

    private final ArmorProgression
            toArmorProgression(final Iterable<Map<String, Object>> record) {
        final String name;
        final Collection<ArmorLevel> levels;

        levels = StreamSupport.stream(record.spliterator(), false)
            .map(this::toArmorLevel)
            .collect(Collectors.toList());

        name = StreamSupport.stream(record.spliterator(), false)
            .map((data) -> (String) data.getOrDefault("armor", ""))
            .findAny()
            .orElse("");

        return new ImmutableArmorProgression(name, levels);
    }

}
