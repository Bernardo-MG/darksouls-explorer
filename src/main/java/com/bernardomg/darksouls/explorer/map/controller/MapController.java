
package com.bernardomg.darksouls.explorer.map.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.item.request.DefaultItemRequest;
import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.map.service.MapService;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService service;

    public MapController(final MapService service) {
        super();

        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Map> read(final DefaultItemRequest request,
            final Pageable page) {
        return service.getAll(page);
    }

    @GetMapping(path = "/connections",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<MapConnection> readConnections(final DefaultItemRequest request,
            final Pageable page) {
        return service.getAllConnections(page);
    }

}
