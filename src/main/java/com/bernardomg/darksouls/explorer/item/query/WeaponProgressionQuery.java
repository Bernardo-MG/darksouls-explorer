
package com.bernardomg.darksouls.explorer.item.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class WeaponProgressionQuery
        implements Query<List<WeaponProgression>> {

    public WeaponProgressionQuery() {
        super();
    }

    @Override
    public final List<WeaponProgression>
            getOutput(final Iterable<Map<String, Object>> record) {
        return Arrays.asList(toWeaponProgression(record));
    }

    @Override
    public final String getStatement() {
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

    private final WeaponLevel toWeaponLevel(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
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

    private final WeaponProgression
            toWeaponProgression(final Iterable<Map<String, Object>> record) {
        final String name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String> pathNames;
        Collection<WeaponLevel> levels;
        WeaponProgressionPath path;

        pathNames = StreamSupport.stream(record.spliterator(), false)
            .map((data) -> (String) data.getOrDefault("path", ""))
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        paths = new ArrayList<>();
        for (final String pathName : pathNames) {
            levels = StreamSupport.stream(record.spliterator(), false)
                .filter(
                    (data) -> pathName.equals(data.getOrDefault("path", "")))
                .map(this::toWeaponLevel)
                .collect(Collectors.toList());

            path = new ImmutableWeaponProgressionPath(pathName, levels);
            paths.add(path);
        }

        name = StreamSupport.stream(record.spliterator(), false)
            .map((data) -> (String) data.getOrDefault("weapon", ""))
            .findAny()
            .orElse("");

        return new ImmutableWeaponProgression(name, paths);
    }

}
