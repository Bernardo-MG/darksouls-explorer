
package com.bernardomg.darksouls.explorer.item.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.service.ItemProgressionService;

@RestController
@RequestMapping("/items")
public class ItemProgressionController {

    private final ItemProgressionService service;

    public ItemProgressionController(final ItemProgressionService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(path = "/{id}/progression/armors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArmorProgression readArmorLevels(@PathVariable("id") final Long id) {
        return service.getArmorProgression(id)
            .orElse(null);
    }

    @GetMapping(path = "/{id}/progression/weapons",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponProgression
            readWeaponLevels(@PathVariable("id") final Long id) {
        return service.getWeaponProgression(id)
            .orElse(null);
    }

}
