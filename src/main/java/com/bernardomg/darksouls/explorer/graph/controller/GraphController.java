
package com.bernardomg.darksouls.explorer.graph.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Graph> read(
            @RequestParam(value = "links") final List<String> links) {
        final Graph graph;

        graph = service.getGraph(links);
        return new ResponseEntity<>(graph, HttpStatus.OK);
    }

    @GetMapping(path = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<String>> readLinks() {
        final Iterable<String> links;

        links = service.getLinks();
        return new ResponseEntity<>(links, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Info> readNode(@PathVariable final Long id) {
        final Info info;

        info = service.getInfo(id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

}
