
package com.bernardomg.darksouls.explorer.item.talisman.controller;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.talisman.domain.ImmutableTalisman;
import com.bernardomg.darksouls.explorer.item.talisman.domain.Talisman;
import com.bernardomg.darksouls.explorer.item.talisman.service.TalismanService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/talismans")
public class TalismanController {

    private final TalismanService service;

    public TalismanController(final TalismanService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends Talisman>
            read(@RequestParam(name = "selectors",
                    defaultValue = "") final Collection<String> selectors,
                    final Pagination pagination, final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Talisman readOne(@PathVariable("id") final Long id) {
        final Optional<? extends Talisman> read;
        final Talisman result;

        read = service.getOne(id);
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = new ImmutableTalisman();
        }

        return result;
    }

}
