
package com.bernardomg.darksouls.explorer.graph.query.processor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.darksouls.explorer.graph.model.DefaultGraph;
import com.bernardomg.darksouls.explorer.graph.model.DefaultLink;
import com.bernardomg.darksouls.explorer.graph.model.DefaultNode;
import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Link;
import com.bernardomg.darksouls.explorer.graph.model.Node;

public final class GraphProcessor implements Processor<Graph> {

    /**
     * Logger.
     */
    private static final Logger LOGGER     = LoggerFactory
        .getLogger(GraphProcessor.class);

    private final List<String>  linkFields = Arrays.asList("source", "sourceId",
        "target", "targetId", "relationship");

    public GraphProcessor() {
        super();
    }

    @Override
    public final Graph process(final Collection<Map<String, Object>> rows) {
        final List<Link> links;
        final Set<Node> nodes;
        final Set<String> resultTypes;
        final Graph result;

        links = rows.stream()
            .map(this::toLink)
            .collect(Collectors.toList());

        // TODO: Build only if they are requested

        nodes = new HashSet<>();
        nodes.addAll(rows.stream()
            .map(this::toSourceNode)
            .collect(Collectors.toList()));
        nodes.addAll(rows.stream()
            .map(this::toTargetNode)
            .collect(Collectors.toList()));

        resultTypes = new HashSet<>();
        resultTypes.addAll(links.stream()
            .map(Link::getType)
            .collect(Collectors.toList()));

        result = new DefaultGraph();
        result.setLinks(links);
        result.setNodes(nodes);
        result.setTypes(resultTypes);

        return result;
    }

    private final Link toLink(final Map<String, Object> row) {
        final Link result;

        LOGGER.trace("Mapping row {} to link", row);

        result = new DefaultLink();
        result.setSourceLabel((String) row.getOrDefault("source", ""));
        result.setSource((Long) row.getOrDefault("sourceId", 0l));
        result.setTargetLabel((String) row.getOrDefault("target", ""));
        result.setTarget((Long) row.getOrDefault("targetId", 0l));
        result.setType((String) row.getOrDefault("relationship", ""));

        for (final Entry<String, Object> pair : row.entrySet()) {
            if (!linkFields.contains(pair.getKey())) {
                result.addAttribute(pair.getKey(), pair.getValue());
            }
        }

        LOGGER.debug("Mapped row {} to {}", row, result);

        return result;
    }

    private final Node toSourceNode(final Map<String, Object> row) {
        final Node result;

        LOGGER.trace("Mapping row {} to source node", row);

        result = new DefaultNode((Long) row.getOrDefault("sourceId", 0l),
            (String) row.getOrDefault("source", ""));

        LOGGER.debug("Mapped row {} to {}", row, result);

        return result;
    }

    private final Node toTargetNode(final Map<String, Object> row) {
        final Node result;

        LOGGER.trace("Mapping row {} to target node", row);

        result = new DefaultNode((Long) row.getOrDefault("targetId", 0l),
            (String) row.getOrDefault("target", ""));

        LOGGER.debug("Mapped row {} to {}", row, result);

        return result;
    }

}
