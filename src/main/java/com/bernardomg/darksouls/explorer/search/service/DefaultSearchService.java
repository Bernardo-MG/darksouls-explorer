
package com.bernardomg.darksouls.explorer.search.service;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.search.domain.SearchRequest;
import com.bernardomg.darksouls.explorer.search.domain.SearchResult;
import com.bernardomg.darksouls.explorer.search.query.SearchQuery;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.persistence.executor.Query;
import com.bernardomg.persistence.executor.QueryExecutor;

@Service
public final class DefaultSearchService implements SearchService {

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultSearchService(final QueryExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Iterable<SearchResult> search(final SearchRequest search,
            final Pagination pagination, final Sort sort) {
        final Query<SearchResult> query;

        query = new SearchQuery(search.getName());

        return queryExecutor.fetch(query::getStatement, query::getOutput,
            pagination, Arrays.asList(sort));
    }

}
