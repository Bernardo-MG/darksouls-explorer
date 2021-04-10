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

package com.bernardomg.darksouls.explorer.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

@Service
public final class DefaultGraphqlService implements GraphqlService {

    /**
     * GraphQL executor.
     */
    private final GraphQL graphql;

    /**
     * Constructs a service with the received executor.
     * 
     * @param gql
     *            GraphQL executor
     */
    @Autowired
    public DefaultGraphqlService(final GraphQL gql) {
        super();

        graphql = checkNotNull(gql);
    }

    @Override
    public final ExecutionResult execute(final String query,
            final Map<String, Object> variables) {
        final ExecutionInput executionInput;

        executionInput = ExecutionInput.newExecutionInput().query(query)
                .variables(variables).build();
        return graphql.execute(executionInput);
    }

}
