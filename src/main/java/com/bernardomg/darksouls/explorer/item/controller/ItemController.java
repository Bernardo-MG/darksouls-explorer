
package com.bernardomg.darksouls.explorer.item.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.request.DefaultItemRequest;
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
    public Page<Item> read(final DefaultItemRequest request,
            final Pageable page) {
        return service.getAll(request, page);
    }

    @GetMapping(path = "/{id}/levels/armors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArmorProgression readArmorLevels(@PathVariable("id") final Long id) {
        return service.getArmorLevels(id);
    }

    @GetMapping(path = "/{id}/sources",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ItemSource> readSources(@PathVariable("id") final Long id,
            final Pageable page) {
        return service.getSources(id, page);
    }

    @GetMapping(path = "/{id}/levels/weapons",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponProgression
            readWeaponLevels(@PathVariable("id") final Long id) {
        return service.getWeaponLevels(id);
    }

}
