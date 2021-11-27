
package com.bernardomg.darksouls.explorer.item.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;
import com.bernardomg.darksouls.explorer.item.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    public ItemController(final ItemService service) {
        super();

        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Item> read(final Pageable page) {
        return service.getAll(page);
    }

    @GetMapping(path = "/{id}/sources",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ItemSource> readSources(@PathVariable("id") final Long itemId,
            final Pageable page) {
        return service.getSources(itemId, page);
    }

}
