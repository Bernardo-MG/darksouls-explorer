
package com.bernardomg.darksouls.explorer.graph.query.processor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Value;
import org.neo4j.driver.util.Pair;

import com.bernardomg.darksouls.explorer.graph.model.DefaultGraph;
import com.bernardomg.darksouls.explorer.graph.model.DefaultLink;
import com.bernardomg.darksouls.explorer.graph.model.DefaultNode;
import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Link;
import com.bernardomg.darksouls.explorer.graph.model.Node;

public final class GraphProcessor implements Processor<Graph> {

    private final List<String> linkFields = Arrays.asList("source", "sourceId",
            "target", "targetId", "relationship");

    public GraphProcessor() {
        super();
    }

    @Override
    public final Graph process(final Result rows) {
        final List<Link> links;
        final Set<Node> nodes;
        final Set<String> resultTypes;
        final Graph result;

        links = rows.stream().map(this::toLink).collect(Collectors.toList());

        // TODO: Build only if they are requested

        nodes = new HashSet<>();
        nodes.addAll(links.stream().map(this::toSourceNode)
                .collect(Collectors.toList()));
        nodes.addAll(links.stream().map(this::toTargetNode)
                .collect(Collectors.toList()));

        resultTypes = new HashSet<>();
        resultTypes.addAll(
                links.stream().map(Link::getType).collect(Collectors.toList()));

        result = new DefaultGraph();
        result.setLinks(links);
        result.setNodes(nodes);
        result.setTypes(resultTypes);

        return result;
    }

    private final Link toLink(final Record row) {
        final Link relationship;

        relationship = new DefaultLink();
        relationship.setSource(row.get("source", ""));
        relationship.setSourceId(row.get("sourceId", 0l));
        relationship.setTarget(row.get("target", ""));
        relationship.setTargetId(row.get("targetId", 0l));
        relationship.setType(row.get("relationship", ""));

        for (final Pair<String, Value> pair : row.fields()) {
            if (!linkFields.contains(pair.key())) {
                relationship.addAttribute(pair.key(), pair.value().asObject());
            }
        }

        return relationship;
    }

    private final Node toSourceNode(final Link link) {
        final Node result;

        result = new DefaultNode();
        result.setId(link.getSourceId());
        result.setName(link.getSource());

        return result;
    }

    private final Node toTargetNode(final Link link) {
        final Node result;

        result = new DefaultNode();
        result.setId(link.getTargetId());
        result.setName(link.getTarget());

        return result;
    }

}
