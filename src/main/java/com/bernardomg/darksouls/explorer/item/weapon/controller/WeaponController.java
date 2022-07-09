
package com.bernardomg.darksouls.explorer.item.weapon.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponSummary;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.DtoWeaponAdjustment;
import com.bernardomg.darksouls.explorer.item.weapon.domain.adjustment.WeaponAdjustment;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.DtoWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.service.WeaponService;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@RestController
@RequestMapping("/weapons")
public class WeaponController {

    private final WeaponService service;

    public WeaponController(final WeaponService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends WeaponSummary> read(
            @RequestParam(name = "type", required = false, defaultValue = "") final String type,
            final Pagination pagination, final Sort sort) {
        return service.getAll(type, pagination, sort);
    }

    @GetMapping(path = "/{id}/adjustment", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponAdjustment readAdjustments(@PathVariable("id") final Long id) {
        return service.getAdjustment(id)
            .orElse(new DtoWeaponAdjustment());

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Weapon readOne(@PathVariable("id") final Long id) {
        return service.getOne(id)
            .orElse(new DtoWeapon());
    }

    @GetMapping(path = "/{id}/progression", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponProgression readProgressions(@PathVariable("id") final Long id) {
        return service.getProgression(id)
            .orElse(new DtoWeaponProgression());
    }

}
