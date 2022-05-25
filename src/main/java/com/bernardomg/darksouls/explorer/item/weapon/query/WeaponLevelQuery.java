
package com.bernardomg.darksouls.explorer.item.weapon.query;

import java.util.Map;

import com.bernardomg.darksouls.explorer.item.weapon.domain.ImmutableWeaponLevel;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class WeaponLevelQuery implements Query<WeaponLevel> {

    public WeaponLevelQuery() {
        super();
    }

    @Override
    public final WeaponLevel getOutput(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
            String.valueOf(record.getOrDefault("weapon", "")),
            String.valueOf(record.getOrDefault("path", "")),
            ((Number) record.getOrDefault("level", 0)).intValue(),
            ((Number) record.getOrDefault("pathLevel", 0)).intValue(),
            ((Number) record.getOrDefault("physicalDamage", 0)).intValue(),
            ((Number) record.getOrDefault("magicDamage", 0)).intValue(),
            ((Number) record.getOrDefault("fireDamage", 0)).intValue(),
            ((Number) record.getOrDefault("lightningDamage", 0)).intValue(),
            String.valueOf(record.getOrDefault("strengthBonus", "")),
            String.valueOf(record.getOrDefault("dexterityBonus", "")),
            String.valueOf(record.getOrDefault("intelligenceBonus", "")),
            String.valueOf(record.getOrDefault("faithBonus", "")),
            ((Number) record.getOrDefault("physicalReduction", 0)).floatValue(),
            ((Number) record.getOrDefault("magicReduction", 0)).floatValue(),
            ((Number) record.getOrDefault("fireReduction", 0)).floatValue(),
            ((Number) record.getOrDefault("lightningReduction", 0))
                .floatValue());
    }

    @Override
    public final String getStatement(final Map<String, Object> params) {
        final String query;

        query =
        // @formatter:off
          "MATCH" + System.lineSeparator()
        + "   (w)-[HAS_LEVEL]->(l:Level) " + System.lineSeparator()
        + "WHERE" + System.lineSeparator()
        + "   id(w) = $id" + System.lineSeparator()
        + "   AND (w:Weapon OR w:Shield)" + System.lineSeparator()
        + "OPTIONAL MATCH " + System.lineSeparator()
        + "   (p:Level)-[NEXT]->(l) " + System.lineSeparator()
        + "RETURN" + System.lineSeparator()
        + "   l.target AS weapon," + System.lineSeparator()
        + "   l.path AS path," + System.lineSeparator()
        + "   l.level AS level," + System.lineSeparator()
        + "   l.pathLevel AS pathLevel," + System.lineSeparator()
        + "   l.physicalDamage AS physicalDamage," + System.lineSeparator()
        + "   l.magicDamage AS magicDamage," + System.lineSeparator()
        + "   l.fireDamage AS fireDamage," + System.lineSeparator()
        + "   l.lightningDamage AS lightningDamage," + System.lineSeparator()
        + "   l.strengthBonus AS strengthBonus," + System.lineSeparator()
        + "   l.dexterityBonus AS dexterityBonus," + System.lineSeparator()
        + "   l.intelligenceBonus AS intelligenceBonus," + System.lineSeparator()
        + "   l.faithBonus AS faithBonus," + System.lineSeparator()
        + "   l.physicalReduction AS physicalReduction," + System.lineSeparator()
        + "   l.magicReduction AS magicReduction," + System.lineSeparator()
        + "   l.fireReduction AS fireReduction," + System.lineSeparator()
        + "   l.lightningReduction AS lightningReduction" + System.lineSeparator()
        + "ORDER BY" + System.lineSeparator()
        + "   weapon ASC," + System.lineSeparator()
        + "   path ASC," + System.lineSeparator()
        + "   level ASC";
        // @formatter:on;

        return query;
    }

}
