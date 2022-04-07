
package com.bernardomg.darksouls.explorer.item.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemSourceController {

    private final ItemService service;

    public ItemSourceController(final ItemService service) {
        super();

        this.service = service;
    }

    @GetMapping(path = "/{id}/sources",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ItemSource> readSources(@PathVariable("id") final Long id,
            final Pageable page) {
        return service.getSources(id, page);
    }

}
