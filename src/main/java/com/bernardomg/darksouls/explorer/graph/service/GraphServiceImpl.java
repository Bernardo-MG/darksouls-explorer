
package com.bernardomg.darksouls.explorer.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Info;
import com.bernardomg.darksouls.explorer.graph.query.GraphQueries;

import graphql.com.google.common.collect.Iterables;

@Service
public final class GraphServiceImpl implements GraphService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(GraphServiceImpl.class);

    private final GraphQueries  queries;

    @Autowired
    public GraphServiceImpl(final GraphQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Graph getGraph(final Iterable<String> relationships) {
        final Graph graph;

        LOGGER.debug("Filtering by links: {}", relationships);

        if (Iterables.isEmpty(relationships)) {
            graph = queries.findAll();
        } else {
            graph = queries.findAllByLinkType(relationships);
        }

        return graph;
    }

    @Override
    public final Info getInfo(final Long id) {
        return queries.findById(id).orElse(null);
    }

    @Override
    public Iterable<String> getLinks() {
        return queries.findAllLinks();
    }

}
