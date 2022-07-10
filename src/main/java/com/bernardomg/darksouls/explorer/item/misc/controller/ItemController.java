
package com.bernardomg.darksouls.explorer.item.misc.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.domain.Summary;
import com.bernardomg.darksouls.explorer.item.misc.domain.DtoItem;
import com.bernardomg.darksouls.explorer.item.misc.domain.Item;
import com.bernardomg.darksouls.explorer.item.misc.service.ItemService;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    public ItemController(final ItemService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Summary> read(@RequestParam(name = "type", required = false, defaultValue = "") final String type,
            final Pagination pagination, final Sort sort) {
        return service.getAll(type, pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Item readOne(@PathVariable("id") final Long id) {
        return service.getOne(id)
            .orElse(new DtoItem());
    }

}
