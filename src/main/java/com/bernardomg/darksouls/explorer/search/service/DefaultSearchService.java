
package com.bernardomg.darksouls.explorer.search.service;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.search.domain.SearchRequest;
import com.bernardomg.darksouls.explorer.search.domain.SearchResult;
import com.bernardomg.darksouls.explorer.search.query.SearchQuery;

@Service
public final class DefaultSearchService implements SearchService {

    private final QueryExecutor<String> queryExecutor;

    @Autowired
    public DefaultSearchService(final QueryExecutor<String> queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Iterable<SearchResult> search(final SearchRequest search,
            final Pagination pagination, final Sort sort) {
        final Query<SearchResult> query;

        query = new SearchQuery(search.getName());

        return queryExecutor.fetch(query.getStatement(), query::getOutput,
            pagination, Arrays.asList(sort));
    }

}
