
package com.bernardomg.darksouls.explorer.item.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemLevelController {

    private final ItemService service;

    public ItemLevelController(final ItemService service) {
        super();

        this.service = service;
    }

    @GetMapping(path = "/{id}/levels/armors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArmorProgression readArmorLevels(@PathVariable("id") final Long id) {
        return service.getArmorLevels(id);
    }

    @GetMapping(path = "/{id}/levels/weapons",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponProgression
            readWeaponLevels(@PathVariable("id") final Long id) {
        return service.getWeaponLevels(id);
    }

}
