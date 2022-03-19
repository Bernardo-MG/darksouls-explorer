
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.persistence.TextQuery;

public final class ArmorProgressionQuery
        implements TextQuery<Map<String, Object>, Map<String, Object>> {

    public ArmorProgressionQuery() {
        super();
    }

    @Override
    public final Map<String, Object>
            getOutput(final Map<String, Object> record) {
        return record;
    }

    @Override
    public final String getStatement() {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "   (a:Armor)-[HAS_LEVEL]->(l:Level) " + System.lineSeparator()
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
