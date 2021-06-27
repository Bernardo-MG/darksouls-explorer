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

package com.bernardomg.darksouls.explorer.graphql;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.model.Graph;
import com.bernardomg.darksouls.explorer.persistence.repository.GraphRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * Fetcher for graphs. It will return all the nodes and their relationships.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Component
public final class GraphDataFetcher implements DataFetcher<Graph> {

    /**
     * Graph repository.
     */
    @Autowired
    private GraphRepository graphRepository;

    /**
     * Default constructor.
     */
    public GraphDataFetcher() {
        super();
    }

    @Override
    public final Graph get(final DataFetchingEnvironment environment)
            throws Exception {
        final List<String> types;
        final Graph result;

        types = environment.getArgumentOrDefault("type",
                Collections.emptyList());
        if (types.isEmpty()) {
            result = graphRepository.findAll();
        } else {
            result = graphRepository.findAllByLinkType(types);
        }

        return result;
    }

}
