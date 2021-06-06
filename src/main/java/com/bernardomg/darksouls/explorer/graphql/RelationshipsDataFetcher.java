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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.model.Relationship;
import com.bernardomg.darksouls.explorer.persistence.repository.RelationshipRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * Fetcher for relationships. It will return all the nodes connected by a
 * relationship, along said relationship.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Component
public final class RelationshipsDataFetcher
        implements DataFetcher<Iterable<Relationship>> {

    /**
     * Planets repository.
     */
    @Autowired
    private RelationshipRepository mentionRepository;

    /**
     * Default constructor.
     */
    public RelationshipsDataFetcher() {
        super();
    }

    @Override
    public Iterable<Relationship> get(final DataFetchingEnvironment environment)
            throws Exception {
        // TODO: Handle when the argument is missing
        return mentionRepository.findAll(environment.getArgument("type"));
    }

}
