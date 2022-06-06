
package com.bernardomg.darksouls.explorer.item.armor.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorLevel;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorLevel;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.utils.Maps;

public final class ArmorLevelQuery implements Query<ArmorLevel> {

    public ArmorLevelQuery() {
        super();
    }

    @Override
    public final ArmorLevel getOutput(final Map<String, Object> record) {
        return new ImmutableArmorLevel(
            String.valueOf(record.getOrDefault("armor", "")),
            ((Number) Maps.getOrDefault(record, "level", 0)).intValue(),
            Maps.getOrDefault(record, "regularProtection", 0f),
            Maps.getOrDefault(record, "strikeProtection", 0f),
            Maps.getOrDefault(record, "slashProtection", 0f),
            Maps.getOrDefault(record, "thrustProtection", 0f),
            Maps.getOrDefault(record, "magicProtection", 0f),
            Maps.getOrDefault(record, "fireProtection", 0f),
            Maps.getOrDefault(record, "lightningProtection", 0f),
            Maps.getOrDefault(record, "bleedProtection", 0f),
            Maps.getOrDefault(record, "poisonProtection", 0f),
            Maps.getOrDefault(record, "curseProtection", 0f));
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
