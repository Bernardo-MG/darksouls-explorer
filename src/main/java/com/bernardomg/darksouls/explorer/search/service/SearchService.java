
package com.bernardomg.darksouls.explorer.search.service;

import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.search.domain.SearchRequest;
import com.bernardomg.darksouls.explorer.search.domain.SearchResult;

public interface SearchService {

    public Iterable<SearchResult> search(final SearchRequest search,
            final Pagination pagination, final Sort sort);

}
