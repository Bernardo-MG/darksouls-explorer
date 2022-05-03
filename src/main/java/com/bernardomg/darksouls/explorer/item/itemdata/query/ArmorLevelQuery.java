
package com.bernardomg.darksouls.explorer.item.itemdata.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorLevel;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ArmorLevelQuery implements Query<ArmorLevel> {

    public ArmorLevelQuery() {
        super();
    }

    @Override
    public final ArmorLevel getOutput(final Map<String, Object> record) {
        return new ImmutableArmorLevel(
            String.valueOf(record.getOrDefault("armor", "")),
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

    @Override
    public final String getStatement(final Map<String, Object> params) {
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

}
