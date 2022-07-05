
package com.bernardomg.darksouls.explorer.search.service;

import com.bernardomg.darksouls.explorer.search.domain.SearchRequest;
import com.bernardomg.darksouls.explorer.search.domain.SearchResult;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

public interface SearchService {

    public Iterable<SearchResult> search(final SearchRequest search, final Pagination pagination, final Sort sort);

}
