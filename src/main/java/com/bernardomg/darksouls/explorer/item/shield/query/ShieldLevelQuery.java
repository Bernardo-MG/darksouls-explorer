
package com.bernardomg.darksouls.explorer.item.shield.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.item.domain.DtoWeaponLevelNode;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.utils.Maps;

public final class ShieldLevelQuery implements Query<DtoWeaponLevelNode> {

    public ShieldLevelQuery() {
        super();
    }

    @Override
    public final DtoWeaponLevelNode
            getOutput(final Map<String, Object> record) {
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
        + "   AND (w:Shield)" + System.lineSeparator()
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
