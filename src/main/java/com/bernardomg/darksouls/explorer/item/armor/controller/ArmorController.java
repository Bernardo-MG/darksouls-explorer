
package com.bernardomg.darksouls.explorer.item.armor.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.armor.domain.Armor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;
import com.bernardomg.darksouls.explorer.item.armor.domain.ImmutableArmorProgression;
import com.bernardomg.darksouls.explorer.item.armor.service.ArmorService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/armors")
public class ArmorController {

    private final ArmorService service;

    public ArmorController(final ArmorService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends Armor> read(final Pagination pagination,
            final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/{id}/progression",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArmorProgression readArmorLevels(@PathVariable("id") final Long id) {
        return service.getProgression(id)
            .orElse(new ImmutableArmorProgression());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Armor readOne(@PathVariable("id") final Long id) {
        final Optional<? extends Armor> read;
        final Armor result;

        read = service.getOne(id);
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = new DtoArmor();
        }

        return result;
    }

}
