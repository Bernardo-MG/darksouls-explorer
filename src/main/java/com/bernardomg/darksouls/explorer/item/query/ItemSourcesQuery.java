
package com.bernardomg.darksouls.explorer.item.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemMerchantSource;
import com.bernardomg.darksouls.explorer.item.domain.ImmutableItemSource;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.persistence.model.Query;

public final class ItemSourcesQuery implements Query<List<ItemSource>> {

    public ItemSourcesQuery() {
        super();
    }

    @Override
    public final List<ItemSource>
            getOutput(final Iterable<Map<String, Object>> record) {
        return StreamSupport.stream(record.spliterator(), false)
            .map(this::toItemSource)
            .collect(Collectors.toList());
    }

    @Override
    public final String getStatement() {
        final Collection<String> rels;
        final String joinedRels;
        final String queryTemplate;

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
          + "  id(i) AS itemId," + System.lineSeparator()
          + "  i.name AS item," + System.lineSeparator()
          + "  id(s) AS sourceId," + System.lineSeparator()
          + "  s.name AS source," + System.lineSeparator()
          + "  rel.price AS price," + System.lineSeparator()
          + "  type(rel) AS relationship," + System.lineSeparator()
          + "  id(l) AS locationId," + System.lineSeparator()
          + "  l.name AS location";
        // @formatter:on;

        // TODO: Use parameters
        joinedRels = rels.stream()
            .collect(Collectors.joining("|"));
        return String.format(queryTemplate, joinedRels);
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

}
