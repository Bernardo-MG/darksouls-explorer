
package com.bernardomg.darksouls.explorer.item.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.model.Item;
import com.bernardomg.darksouls.explorer.item.model.ItemSource;
import com.bernardomg.darksouls.explorer.item.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ItemController.class);

    private final ItemService   service;

    public ItemController(final ItemService service) {
        super();

        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Item> read(final Pageable page) {
        return service.getAll(page);
    }

    @GetMapping(path = "/sources", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ItemSource> readSources() {
        return service.getAllSources();
    }

}
