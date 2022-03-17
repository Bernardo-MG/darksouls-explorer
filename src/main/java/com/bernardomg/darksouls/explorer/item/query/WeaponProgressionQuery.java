
package com.bernardomg.darksouls.explorer.item.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.persistence.StringQuery;

public final class WeaponProgressionQuery implements
        StringQuery<Collection<Map<String, Object>>, WeaponProgression> {

    public WeaponProgressionQuery() {
        super();
    }

    @Override
    public final WeaponProgression
            getOutput(final Collection<Map<String, Object>> record) {
        final String name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String> pathNames;
        Collection<WeaponLevel> levels;
        WeaponProgressionPath path;

        pathNames = record.stream()
            .map((data) -> (String) data.getOrDefault("path", ""))
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        paths = new ArrayList<>();
        for (final String pathName : pathNames) {
            levels = record.stream()
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

    private final WeaponLevel toWeaponLevel(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
            ((Long) record.getOrDefault("level", 0l)).intValue(),
            ((Long) record.getOrDefault("pathLevel", 0l)).intValue(),
            ((Long) record.getOrDefault("physicalDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("magicDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("fireDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("lightningDamage", 0l)).intValue());
    }

}