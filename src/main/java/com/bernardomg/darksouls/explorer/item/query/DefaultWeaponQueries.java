
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    public final WeaponProgression findWeaponSources(final String weapon) {
        final String query;
        final Collection<Map<String, Object>> levelsInfo;
        final Collection<WeaponLevel> levels;
        final Map<String, Object> params;
        final String name;
        final String pathName;
        final Iterable<WeaponProgressionPath> paths;
        final WeaponProgressionPath path;

        params = new HashMap<>();
        params.put("weapon", weapon);

        query =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "   (l:WeaponLevel)" + System.lineSeparator()
          + "WHERE" + System.lineSeparator()
          + "  l.weapon = $weapon" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "   l.weapon AS weapon," + System.lineSeparator()
          + "   l.path AS path," + System.lineSeparator()
          + "   l.level AS level," + System.lineSeparator()
          + "   l.physicalDamage AS physicalDamage";
        // @formatter:on;

        levelsInfo = queryExecutor.fetch(query, params);

        levels = levelsInfo.stream()
            .map(this::toWeaponLevel)
            .collect(Collectors.toList());
        // FIXME: Handle empty list
        name = (String) levelsInfo.iterator()
            .next()
            .getOrDefault("weapon", "");
        // FIXME: Handle empty list
        pathName = (String) levelsInfo.iterator()
            .next()
            .getOrDefault("path", "");

        path = new ImmutableWeaponProgressionPath(pathName, levels);

        paths = Arrays.asList(path);

        return new ImmutableWeaponProgression(name, paths);
    }

    private final WeaponLevel toWeaponLevel(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
            ((Long) record.getOrDefault("level", 0l)).intValue(),
            ((Long) record.getOrDefault("physicalLevel", 0l)).intValue());
    }

}
