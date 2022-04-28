
package com.bernardomg.darksouls.explorer.item.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.service.ItemService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/items")
public class ItemSourceController {

    private final ItemService service;

    public ItemSourceController(final ItemService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(path = "/{id}/sources",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ItemSource> readSources(@PathVariable("id") final Long id,
            final Pagination pagination, final Sort sort) {
        return service.getSources(id, pagination, sort);
    }

}
