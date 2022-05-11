
package com.bernardomg.darksouls.explorer.item.talisman.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.DefaultCatalystRequest;
import com.bernardomg.darksouls.explorer.item.catalyst.service.CatalystService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/catalysts")
public class TalismanController {

    private final CatalystService service;

    public TalismanController(final CatalystService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Catalyst> read(
            @RequestParam(name = "name", defaultValue = "") final String name,
            @RequestParam(name = "selectors",
                    defaultValue = "") final Collection<String> selectors,
            final Pagination pagination, final Sort sort) {
        final DefaultCatalystRequest request;

        request = new DefaultCatalystRequest();
        request.setName(name);

        return service.getAll(request, pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Catalyst readOne(@PathVariable("id") final Long id) {
        return service.getOne(id)
            .orElse(null);
    }

}
