
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.persistence.TextQuery;

public final class WeaponProgressionQuery
        implements TextQuery<Map<String, Object>, Map<String, Object>> {

    public WeaponProgressionQuery() {
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
          + "   (w:Weapon)-[HAS_LEVEL]->(l:Level) " + System.lineSeparator()
          + "WHERE" + System.lineSeparator()
          + "  id(w) = $id" + System.lineSeparator()
          + "OPTIONAL MATCH " + System.lineSeparator()
          + "   (p:Level)-[NEXT]->(l) " + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "   l.weapon AS weapon," + System.lineSeparator()
          + "   l.path AS path," + System.lineSeparator()
          + "   l.level AS level," + System.lineSeparator()
          + "   COALESCE(p.level, -1) + 1 AS pathLevel," + System.lineSeparator()
          + "   l.physicalDamage AS physicalDamage," + System.lineSeparator()
          + "   l.magicDamage AS magicDamage," + System.lineSeparator()
          + "   l.fireDamage AS fireDamage," + System.lineSeparator()
          + "   l.lightningDamage AS lightningDamage" + System.lineSeparator()
          + "ORDER BY" + System.lineSeparator()
          + "   path ASC," + System.lineSeparator()
          + "   level ASC";
        // @formatter:on;

        return query;
    }

}
