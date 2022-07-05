
package com.bernardomg.darksouls.explorer.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.search.domain.SearchRequest;
import com.bernardomg.darksouls.explorer.search.domain.SearchResult;
import com.bernardomg.darksouls.explorer.search.service.SearchService;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService service;

    public SearchController(final SearchService serv) {
        super();

        service = serv;
    }

    @GetMapping
    public Iterable<SearchResult> readSources(final SearchRequest query, final Pagination pagination, final Sort sort) {
        return service.search(query, pagination, sort);
    }

}
