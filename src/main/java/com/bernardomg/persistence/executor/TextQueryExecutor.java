/**
 * Copyright 2021 the original author or authors
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

package com.bernardomg.persistence.executor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Functions;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingReadingAndReturn;
import org.neo4j.cypherdsl.core.SymbolicName;
import org.neo4j.cypherdsl.parser.CypherParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.core.Neo4jClient;

import com.bernardomg.pagination.model.DefaultPageIterable;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public final class TextQueryExecutor implements QueryExecutor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TextQueryExecutor.class);

    private final Neo4jClient   client;

    public TextQueryExecutor(final Neo4jClient clnt) {
        super();

        client = Objects.requireNonNull(clnt);
    }

    @Override
    public final <T> Collection<T> fetch(final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper) {
        final String query;

        query = queryGenerator.apply(Collections.emptyMap());

        LOGGER.debug("Query:\n{}", query);

        // Data is fetched and mapped
        return client.query(query)
            .fetch()
            .all()
            .stream()
            .map(mapper)
            .collect(Collectors.toList());
    }

    @Override
    public final <T> Collection<T> fetch(final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper, final Map<String, Object> parameters) {
        final String query;

        query = queryGenerator.apply(parameters);

        LOGGER.debug("Query:\n{}", query);

        // Data is fetched and mapped
        return client.query(query)
            .bindAll(parameters)
            .fetch()
            .all()
            .stream()
            .map(mapper)
            .collect(Collectors.toList());
    }

    @Override
    public final <T> PageIterable<T> fetch(final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper, final Map<String, Object> parameters,
            final Pagination pagination, final Iterable<Sort> sort) {
        final List<T>          data;
        final Statement        baseStatement;
        final Optional<String> sortOptions;
        String                 finalQuery;
        final String           query;

        query = queryGenerator.apply(parameters);

        baseStatement = CypherParser.parseStatement(query);

        finalQuery = query;

        // Sort
        sortOptions = StreamSupport.stream(sort.spliterator(), false)
            .filter(Sort::getSorted)
            .map(this::getFieldSort)
            .reduce(this::mergeSort);
        if (sortOptions.isPresent()) {
            finalQuery += " ORDER BY " + sortOptions.get();
        }

        // Pagination
        if (pagination.getPaged()) {
            finalQuery += String.format(" SKIP %d", pagination.getPage() * pagination.getSize());
            finalQuery += String.format(" LIMIT %d", pagination.getSize());
        }

        LOGGER.debug("Query: {}", finalQuery);

        // Data is fetched and mapped
        data = client.query(finalQuery)
            .bindAll(parameters)
            .fetch()
            .all()
            .stream()
            .map(mapper)
            .collect(Collectors.toList());

        return getPage(data, pagination, () -> count(baseStatement, parameters));
    }

    @Override
    public final <T> PageIterable<T> fetch(final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper, final Pagination pagination, final Iterable<Sort> sort) {
        return fetch(queryGenerator, mapper, Collections.emptyMap(), pagination, sort);
    }

    @Override
    public final <T> Optional<T> fetchOne(final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper) {
        final Optional<Map<String, Object>> read;
        final T                             mapped;
        final Optional<T>                   result;
        final String                        query;

        query = queryGenerator.apply(Collections.emptyMap());

        LOGGER.debug("Query:\n{}", query);

        // Data is fetched and mapped
        read = client.query(query)
            .fetch()
            .first();
        if (read.isPresent()) {
            mapped = mapper.apply(read.get());
            result = Optional.ofNullable(mapped);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final <T> Optional<T> fetchOne(final Function<Map<String, Object>, String> queryGenerator,
            final Function<Map<String, Object>, T> mapper, final Map<String, Object> parameters) {
        final Optional<Map<String, Object>> read;
        final T                             mapped;
        final Optional<T>                   result;
        final String                        query;

        query = queryGenerator.apply(parameters);

        LOGGER.debug("Query:\n{}", query);

        // Data is fetched and mapped
        read = client.query(query)
            .bindAll(parameters)
            .fetch()
            .first();

        if (read.isPresent()) {
            mapped = mapper.apply(read.get());
            result = Optional.ofNullable(mapped);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    private final Long count(final Statement statement, final Map<String, Object> parameters) {
        final String countQuery;

        countQuery = getCountQuery(statement.getCypher(), parameters);

        LOGGER.debug("Count: {}", countQuery);

        return client.query(countQuery)
            .fetchAs(Long.class)
            .first()
            .get();
    }

    private final String getCountQuery(final String subquery, final Map<String, Object> parameters) {
        final SymbolicName            name;
        final OngoingReadingAndReturn call;
        final Collection<Object>      params;

        params = parameters.entrySet()
            .stream()
            .map(e -> Arrays.asList(e.getKey(), Cypher.literalOf(e.getValue())))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        name = Cypher.name("value");
        call = Cypher.call("apoc.cypher.run")
            .withArgs(Cypher.literalOf(subquery), Cypher.mapOf(params.toArray()))
            .yield(name)
            .returning(Functions.count(name)
                .as("count"));

        return call.build()
            .getCypher();
    }

    private final String getFieldSort(final Sort sort) {
        return String.format("%s %s", sort.getProperty(), sort.getDirection());
    }

    private final <T> PageIterable<T> getPage(final List<T> data, final Pagination pagination,
            final LongSupplier totalElementsSupplier) {
        final DefaultPageIterable<T> result;
        final Integer                totalPages;
        final Boolean                first;
        final Boolean                last;
        final Integer                size;
        final Integer                pageNumber;
        final long                   totalElements;

        result = new DefaultPageIterable<>();
        result.setContent(data);

        if (pagination.getPaged()) {
            totalElements = totalElementsSupplier.getAsLong();
            if (pagination.getSize() > totalElements) {
                totalPages = 1;
            } else {
                totalPages = (int) (totalElements / pagination.getSize());
            }
            size = pagination.getSize();
            pageNumber = pagination.getPage();
        } else {
            totalPages = 1;
            size = IterableUtils.size(result);
            totalElements = size;
            pageNumber = 0;
        }
        first = pagination.getPage() <= 0;
        last = pagination.getPage() >= (totalPages.intValue() - 1);

        result.setFirst(first);
        result.setLast(last);
        result.setElementsInPage(data.size());
        result.setPageNumber(pageNumber);
        result.setSize(size);
        result.setTotalElements(totalElements);
        result.setTotalPages(totalPages);

        return result;
    }

    private final String mergeSort(final String a, final String b) {
        return a + ", " + b;
    }

}
