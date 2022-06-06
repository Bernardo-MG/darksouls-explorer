
package com.bernardomg.darksouls.explorer.item.catalyst.controller;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.catalyst.domain.Catalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.DtoCatalyst;
import com.bernardomg.darksouls.explorer.item.catalyst.domain.request.DefaultCatalystRequest;
import com.bernardomg.darksouls.explorer.item.catalyst.service.CatalystService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/catalysts")
public class CatalystsController {

    private final CatalystService service;

    public CatalystsController(final CatalystService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends Catalyst> read(
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
        final Optional<? extends Catalyst> read;
        final Catalyst result;

        read = service.getOne(id);
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = new DtoCatalyst();
        }

        return result;
    }

}
