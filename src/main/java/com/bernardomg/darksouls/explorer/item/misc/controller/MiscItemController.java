
package com.bernardomg.darksouls.explorer.item.misc.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.misc.domain.DtoMiscItem;
import com.bernardomg.darksouls.explorer.item.misc.domain.MiscItem;
import com.bernardomg.darksouls.explorer.item.misc.service.MiscItemService;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/miscitems")
public class MiscItemController {

    private final MiscItemService service;

    public MiscItemController(final MiscItemService srvc) {
        super();

        service = Objects.requireNonNull(srvc);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<? extends MiscItem> read(final Pagination pagination,
            final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MiscItem readOne(@PathVariable("id") final Long id) {
        final Optional<? extends MiscItem> read;
        final MiscItem result;

        read = service.getOne(id);
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = new DtoMiscItem();
        }

        return result;
    }

}
