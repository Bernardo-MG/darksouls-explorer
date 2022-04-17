
package com.bernardomg.darksouls.explorer.map.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.request.DefaultItemRequest;
import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.map.service.MapService;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService service;

    public MapController(final MapService service) {
        super();

        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageIterable<Map> read(final DefaultItemRequest request,
            final Pagination pagination, final Sort sort) {
        return service.getAll(pagination, sort);
    }

    @GetMapping(path = "/connections",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PageIterable<MapConnection> readConnections(
            final DefaultItemRequest request, final Pagination pagination,
            final Sort sort) {
        return service.getAllConnections(pagination, sort);
    }

}
