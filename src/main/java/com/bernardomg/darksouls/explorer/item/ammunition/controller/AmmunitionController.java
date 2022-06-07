
package com.bernardomg.darksouls.explorer.item.ammunition.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.ammunition.domain.DtoAmmunition;
import com.bernardomg.darksouls.explorer.item.ammunition.service.AmmunitionService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/ammunitions")
public class AmmunitionController {

    private final AmmunitionService service;

    public AmmunitionController(final AmmunitionService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends Ammunition> read(final Pagination pagination,
            final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Ammunition readOne(@PathVariable("id") final Long id) {
        final Optional<? extends Ammunition> read;
        final Ammunition result;

        read = service.getOne(id);
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = new DtoAmmunition();
        }

        return result;
    }

}
