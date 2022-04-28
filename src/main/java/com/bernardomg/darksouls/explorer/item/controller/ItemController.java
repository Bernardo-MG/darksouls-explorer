
package com.bernardomg.darksouls.explorer.item.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.request.DefaultItemRequest;
import com.bernardomg.darksouls.explorer.item.service.ItemService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    public ItemController(final ItemService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Item> read(
            @RequestParam(name = "name", defaultValue = "") final String name,
            @RequestParam(name = "selectors",
                    defaultValue = "") final Collection<String> selectors,
            final Pagination pagination, final Sort sort) {
        final DefaultItemRequest request;

        request = new DefaultItemRequest();
        request.setName(name);
        request.setSelectors(selectors);

        return service.getAll(request, pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Item readOne(@PathVariable("id") final Long id) {
        return service.getOne(id);
    }

}
