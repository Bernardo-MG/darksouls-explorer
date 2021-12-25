
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingWithoutWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableItem;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemMerchantSource;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemSource;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableWeaponProgressionPath;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponLevel;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgressionPath;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;

@Component
public final class DefaultItemQueries implements ItemQueries {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultItemQueries(final Neo4jClient clnt) {
        super();

        queryExecutor = new DefaultQueryExecutor(clnt);
    }

    @Override
    public final Page<Item> findAll(final String name,
            final Iterable<String> tags, final Pageable page) {
        final Node item;
        final Expression nodeName;
        final OngoingReadingWithoutWhere ongoingBuilder;
        final BuildableStatement<ResultStatement> statementBuilder;
        final String[] additionalLabels;
        final Condition nameCondition;

        additionalLabels = StreamSupport.stream(tags.spliterator(), false)
            .toArray(String[]::new);
        item = Cypher.node("Item", additionalLabels)
            .named("s");
        nodeName = item.property("name");

        ongoingBuilder = Cypher.match(item);

        if (!name.isBlank()) {
            nameCondition = nodeName.matches("(?i).*" + name + ".*");
            ongoingBuilder.where(nameCondition);
        }

        statementBuilder = ongoingBuilder.returning(nodeName.as("name"),
            item.property("description")
                .as("description"),
            Functions.id(item)
                .as("id"),
            Functions.labels(item)
                .as("labels"));

        return queryExecutor.fetch(statementBuilder, this::toItem, page);
    }

    @Override
    public final Page<ItemSource> findSources(final Long id,
            final Pageable page) {
        final Map<String, Object> params;
        final Collection<String> rels;
        final String joinedRels;
        final String queryTemplate;
        final String query;

        params = new HashMap<>();
        params.put("id", id);

        // TODO: Include exchanges
        rels = Arrays.asList("DROPS", "SELLS", "STARTS_WITH", "REWARDS",
            "CHOSEN_FROM", "ASCENDS", "LOOT", "DROPS_IN_COMBAT");

        queryTemplate =
        // @formatter:off
            "MATCH" + System.lineSeparator()
          + "  (s)-[rel:%s]->(i:Item)," + System.lineSeparator()
          + "  (s)-[:LOCATED_IN]->(l)" + System.lineSeparator()
          + "WHERE" + System.lineSeparator()
          + "  id(i) = $id" + System.lineSeparator()
          + "RETURN" + System.lineSeparator()
          + "  ID(i) AS itemId, i.name AS item," + System.lineSeparator()
            + "ID(s) AS sourceId, s.name AS source," + System.lineSeparator()
            + "rel.price AS price, type(rel) AS relationship," + System.lineSeparator()
            + "ID(l) AS locationId, l.name AS location";
        // @formatter:on;

        // TODO: Use parameters
        joinedRels = rels.stream()
            .collect(Collectors.joining("|"));
        query = String.format(queryTemplate, joinedRels);
        return queryExecutor.fetch(query, this::toItemSource, params, page);
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

    private final Item toItem(final Map<String, Object> record) {
        final Long id;
        final String name;
        final Iterable<String> description;
        final Iterable<String> tags;
        final Iterable<String> tagsSorted;

        id = (Long) record.getOrDefault("id", Long.valueOf(-1));
        name = (String) record.getOrDefault("name", "");
        description = Arrays.asList(
            ((String) record.getOrDefault("description", "")).split("\\|"));
        tags = (Iterable<String>) record.getOrDefault("labels",
            Collections.emptyList());
        tagsSorted = StreamSupport.stream(tags.spliterator(), false)
            .sorted()
            .collect(Collectors.toList());

        return new ImmutableItem(id, name, description, tagsSorted);
    }

    private final ItemSource toItemSource(final Map<String, Object> record) {
        final String type;
        final String rel;
        final ItemSource source;
        final Number cost;

        rel = (String) record.getOrDefault("relationship", "");

        switch (rel) {
            case "DROPS":
                type = "drop";
                break;
            case "SELLS":
                type = "sold";
                break;
            case "STARTS_WITH":
                type = "starting";
                break;
            case "REWARDS":
                type = "covenant_reward";
                break;
            case "CHOSEN_FROM":
                type = "starting_gift";
                break;
            case "ASCEND":
                type = "ascended";
                break;
            case "LOOT":
                type = "loot";
                break;
            case "DROPS_IN_COMBAT":
                type = "combat_loot";
                break;
            default:
                type = rel;
        }

        switch (rel) {
            case "SELLS":
                cost = (Number) record.getOrDefault("price", 0d);
                source = new ImmutableItemMerchantSource(
                    (Long) record.getOrDefault("itemId", 0l),
                    (String) record.getOrDefault("item", ""),
                    (Long) record.getOrDefault("sourceId", 0l),
                    (String) record.getOrDefault("source", ""), type,
                    (Long) record.getOrDefault("locationId", 0l),
                    (String) record.getOrDefault("location", ""),
                    cost.doubleValue());
                break;
            default:
                source = new ImmutableItemSource(
                    (Long) record.getOrDefault("itemId", 0l),
                    (String) record.getOrDefault("item", ""),
                    (Long) record.getOrDefault("sourceId", 0l),
                    (String) record.getOrDefault("source", ""), type,
                    (Long) record.getOrDefault("locationId", 0l),
                    (String) record.getOrDefault("location", ""));
        }

        return source;
    }

    private final WeaponLevel toWeaponLevel(final Map<String, Object> record) {
        return new ImmutableWeaponLevel(
            ((Long) record.getOrDefault("level", 0l)).intValue(),
            ((Long) record.getOrDefault("physicalLevel", 0l)).intValue());
    }

}
