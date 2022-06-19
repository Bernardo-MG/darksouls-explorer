
package com.bernardomg.darksouls.explorer.item.weapon.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.item.domain.DtoWeaponLevelNode;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevelNode;
import com.bernardomg.pagination.model.Query;
import com.bernardomg.persistence.utils.Maps;

public final class WeaponLevelQuery implements Query<WeaponLevelNode> {

    public WeaponLevelQuery() {
        super();
    }

    @Override
    public final WeaponLevelNode getOutput(final Map<String, Object> record) {
        final DtoWeaponLevelNode result;

        result = new DtoWeaponLevelNode();
        result.setName(Maps.getOrDefault(record, "name", ""));
        result.setPath(Maps.getOrDefault(record, "path", ""));
        result.setLevel(
            ((Number) Maps.getOrDefault(record, "level", 0)).intValue());
        result.setPathLevel(
            ((Number) Maps.getOrDefault(record, "pathLevel", 0)).intValue());

        return result;
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "   (w)-[HAS_LEVEL]->(l:Level) " + System.lineSeparator()
        + "WHERE" + System.lineSeparator()
        + "   l.target = $name" + System.lineSeparator()
        + "OPTIONAL MATCH " + System.lineSeparator()
        + "   (p:Level)-[NEXT]->(l) " + System.lineSeparator()
        + "RETURN" + System.lineSeparator()
        + "   l.target AS name," + System.lineSeparator()
        + "   l.path AS path," + System.lineSeparator()
        + "   l.level AS level," + System.lineSeparator()
        + "   l.pathLevel AS pathLevel" + System.lineSeparator()
        + "ORDER BY" + System.lineSeparator()
        + "   name ASC," + System.lineSeparator()
        + "   path ASC," + System.lineSeparator()
        + "   level ASC";
        // @formatter:on;

        return query;
    }

}
