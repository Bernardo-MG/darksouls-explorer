
package com.bernardomg.darksouls.explorer.item.weapon.controller;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.weapon.domain.DtoWeapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.Weapon;
import com.bernardomg.darksouls.explorer.item.weapon.domain.WeaponSummary;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.ImmutableWeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.domain.path.WeaponProgression;
import com.bernardomg.darksouls.explorer.item.weapon.service.WeaponService;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@RestController
@RequestMapping("/shields")
public class ShieldController {

    private final WeaponService service;

    public ShieldController(
            @Qualifier("ShieldWeaponService") final WeaponService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends WeaponSummary>
            read(@RequestParam(name = "selectors",
                    defaultValue = "") final Collection<String> selectors,
                    final Pagination pagination, final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Weapon readOne(@PathVariable("id") final Long id) {
        final Optional<? extends Weapon> read;
        final Weapon result;

        read = service.getOne(id);
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = new DtoWeapon();
        }

        return result;
    }

    @GetMapping(path = "/{id}/progression",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WeaponProgression
            readProgressions(@PathVariable("id") final Long id) {
        return service.getProgression(id)
            .orElse(new ImmutableWeaponProgression());
    }

}
