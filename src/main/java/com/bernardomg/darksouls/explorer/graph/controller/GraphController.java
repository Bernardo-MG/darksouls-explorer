
package com.bernardomg.darksouls.explorer.graph.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Info;
import com.bernardomg.darksouls.explorer.graph.service.GraphService;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphService service;

    public GraphController(final GraphService service) {
        super();

        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Graph read(@RequestParam(value = "links") final List<String> links) {
        return service.getGraph(links);
    }

    @GetMapping(path = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<String> readLinks() {
        return service.getLinks();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Info readNode(@PathVariable final Long id) {
        return service.getInfo(id);
    }

}
