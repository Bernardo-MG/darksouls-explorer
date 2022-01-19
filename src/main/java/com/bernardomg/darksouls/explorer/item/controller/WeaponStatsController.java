
package com.bernardomg.darksouls.explorer.item.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.domain.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.service.WeaponService;

@RestController
@RequestMapping("/weapons/stats")
public class WeaponStatsController {

    private final WeaponService service;

    public WeaponStatsController(final WeaponService service) {
        super();

        this.service = service;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponProgression
            readWeaponStats(@PathVariable("id") final Long id) {
        return service.getWeaponLevels(id);
    }

}
