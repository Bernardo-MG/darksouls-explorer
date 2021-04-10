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

package com.bernardomg.darksouls.explorer.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.request.DefaultQuery;
import com.bernardomg.darksouls.explorer.service.GraphqlService;

import graphql.ExecutionResult;

/**
 * GraphQL controller. Serves as an access point for GraphQL queries.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@RestController
@RequestMapping("/api")
public class GraphqlController {

    /**
     * GraphQL service.
     */
    private final GraphqlService service;

    /**
     * Constructs a controller with the received service.
     * 
     * @param graphqlService
     *            GraphQL service
     */
    @Autowired
    public GraphqlController(final GraphqlService graphqlService) {
        super();

        service = checkNotNull(graphqlService);
    }

    /**
     * Executes a GraphQL query.
     * 
     * @param query
     *            query to execute
     * @return the result from executing the query
     */
    @PostMapping
    public ResponseEntity<ExecutionResult>
            execute(@RequestBody final DefaultQuery query) {
        final ExecutionResult execution;

        execution = service.execute(query.getQuery(), query.getVariables());
        return new ResponseEntity<>(execution, HttpStatus.OK);
    }

}
