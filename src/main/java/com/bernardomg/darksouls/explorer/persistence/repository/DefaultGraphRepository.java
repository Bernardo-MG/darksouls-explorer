/**
 * Copyright 2020 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.darksouls.explorer.persistence.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bernardomg.darksouls.explorer.model.DefaultGraph;
import com.bernardomg.darksouls.explorer.model.DefaultLink;
import com.bernardomg.darksouls.explorer.model.DefaultNode;
import com.bernardomg.darksouls.explorer.model.Graph;
import com.bernardomg.darksouls.explorer.model.Link;
import com.bernardomg.darksouls.explorer.model.Node;

/**
 * People repository.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Repository
public class DefaultGraphRepository implements GraphRepository {

    private final Driver driver;

    @Autowired
    protected DefaultGraphRepository(final Driver drv) {
        super();

        driver = drv;
    }

    @Override
    public final Graph findAll(final Iterable<String> types) {
        final Result rows;
        final List<Link> links;
        final Set<Node> nodes;
        final Set<String> resultTypes;
        final String queryTemplate;
        final String query;
        final Graph result;
        final Iterable<String> validTypes;

        validTypes = StreamSupport.stream(types.spliterator(), false)
                .map((string) -> "'" + string + "'")
                .collect(Collectors.toList());

        queryTemplate = "MATCH (s)-[r]->(t) WHERE type(r) IN %s RETURN s.name AS source, ID(s) AS sourceId, t.name AS target, ID(t) AS targetId, type(r) AS relationship";
        query = String.format(queryTemplate, validTypes);

        try (Session session = driver.session()) {
            rows = session.run(query);
            links = rows.stream().map(this::toRelationship)
                    .collect(Collectors.toList());
        }

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

    private final Link toRelationship(final Record row) {
        final Link relationship;

        relationship = new DefaultLink();
        relationship.setSource(row.get("source", ""));
        relationship.setSourceId(row.get("sourceId", 0l));
        relationship.setTarget(row.get("target", ""));
        relationship.setTargetId(row.get("targetId", 0l));
        relationship.setType(row.get("relationship", ""));

        return relationship;
    }

    private Node toSourceNode(final Link link) {
        final Node result;

        result = new DefaultNode();
        result.setId(link.getSourceId());
        result.setName(link.getSource());

        return result;
    }

    private Node toTargetNode(final Link link) {
        final Node result;

        result = new DefaultNode();
        result.setId(link.getTargetId());
        result.setName(link.getTarget());

        return result;
    }

}
