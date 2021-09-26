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

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.query.ItemQueries;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * Fetcher for items.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Component
public final class ItemDataFetcher implements DataFetcher<Iterable<Item>> {

    /**
     * Graph repository.
     */
    @Autowired
    private ItemQueries queries;

    /**
     * Default constructor.
     */
    public ItemDataFetcher() {
        super();
    }

    @Override
    public final Iterable<Item> get(final DataFetchingEnvironment environment)
            throws Exception {
        final String name;
        final Iterable<Item> read;

        name = environment.getArgumentOrDefault("name", "");

        if (name.isBlank()) {
            read = queries.findAll();
        } else {
            read = Arrays.asList(queries.findOneByName(name).orElse(null));
        }

        return read;
    }

}
