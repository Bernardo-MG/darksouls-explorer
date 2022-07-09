
package com.bernardomg.darksouls.explorer.item.spell.controller;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.spell.domain.DtoSpell;
import com.bernardomg.darksouls.explorer.item.spell.domain.Spell;
import com.bernardomg.darksouls.explorer.item.spell.domain.SpellSummary;
import com.bernardomg.darksouls.explorer.item.spell.service.SpellService;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@RestController
@RequestMapping("/spells")
public class SpellController {

    private final SpellService service;

    public SpellController(final SpellService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends SpellSummary> read(
            @RequestParam(name = "school", required = false, defaultValue = "") final String school,
            final Pagination pagination, final Sort sort) {
        return service.getAll(school, pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Spell readOne(@PathVariable("id") final Long id) {
        return service.getOne(id)
            .orElse(new DtoSpell());
    }

}
