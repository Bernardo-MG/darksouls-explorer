
package com.bernardomg.darksouls.explorer.metadata.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.metadata.service.MetadataService;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    private final MetadataService service;

    public MetadataController(final MetadataService service) {
        super();

        this.service = service;
    }

    @GetMapping(path = "/tags/{root}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<String> tags(@PathVariable("root") final String root) {
        return service.getTags(root);
    }

}
