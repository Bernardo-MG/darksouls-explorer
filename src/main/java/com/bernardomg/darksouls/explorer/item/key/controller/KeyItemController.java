
package com.bernardomg.darksouls.explorer.item.key.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.ammunition.domain.Ammunition;
import com.bernardomg.darksouls.explorer.item.ammunition.service.AmmunitionService;
import com.bernardomg.darksouls.explorer.item.armor.domain.request.DefaultArmorRequest;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/keyitems")
public class KeyItemController {

    private final AmmunitionService service;

    public KeyItemController(final AmmunitionService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends Ammunition> read(
            @RequestParam(name = "name", defaultValue = "") final String name,
            final Pagination pagination, final Sort sort) {
        final DefaultArmorRequest request;

        request = new DefaultArmorRequest();
        request.setName(name);

        return service.getAll(request, pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Ammunition readOne(@PathVariable("id") final Long id) {
        return service.getOne(id)
            .orElse(null);
    }

}
