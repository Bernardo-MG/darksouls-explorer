
package com.bernardomg.darksouls.explorer.item.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;

@Component
public final class DefaultWeaponQueries implements WeaponQueries {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultWeaponQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final WeaponProgression findWeaponSources(final Long id) {
        final String query;
        final Collection<Map<String, Object>> levelsInfo;
        final Map<String, Object> params;
        final String name;
        final Collection<WeaponProgressionPath> paths;
        final Collection<String> pathNames;
        Collection<WeaponLevel> levels;
        WeaponProgressionPath path;

        params = new HashMap<>();
        params.put("id", id);

        query =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "   (w:Weapon)-[HAS_LEVEL]->(l:WeaponLevel) " + System.lineSeparator()
          + "WHERE" + System.lineSeparator()
          + "  id(w) = $id" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "   l.weapon AS weapon," + System.lineSeparator()
          + "   l.path AS path," + System.lineSeparator()
          + "   l.level AS level," + System.lineSeparator()
          + "   l.physicalDamage AS physicalDamage" + System.lineSeparator()
          + "ORDER BY" + System.lineSeparator()
          + "   path ASC," + System.lineSeparator()
          + "   level ASC";
        // @formatter:on;

        levelsInfo = queryExecutor.fetch(query, params);

        pathNames = levelsInfo.stream()
            .map((data) -> (String) data.getOrDefault("path", ""))
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        paths = new ArrayList<>();
        for (final String pathName : pathNames) {
            levels = levelsInfo.stream()
                .filter(
                    (data) -> pathName.equals(data.getOrDefault("path", "")))
                .map(this::toWeaponLevel)
                .collect(Collectors.toList());

            path = new ImmutableWeaponProgressionPath(pathName, levels);
            paths.add(path);
        }

        name = StreamSupport.stream(levelsInfo.spliterator(), false)
            .map((data) -> (String) data.getOrDefault("weapon", ""))
            .findAny()
            .orElse("");

        return new ImmutableWeaponProgression(name, paths);
    }

    private final WeaponLevel toWeaponLevel(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
            ((Long) record.getOrDefault("level", 0l)).intValue(),
            ((Long) record.getOrDefault("physicalDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("magicDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("fireDamage", 0l)).intValue(),
            ((Long) record.getOrDefault("lightningDamage", 0l)).intValue());
    }

}
