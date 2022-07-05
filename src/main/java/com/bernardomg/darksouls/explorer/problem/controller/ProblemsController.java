
package com.bernardomg.darksouls.explorer.problem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.darksouls.explorer.problem.model.DataProblem;
import com.bernardomg.darksouls.explorer.problem.service.ProblemService;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

@RestController
@RequestMapping("/problems")
public class ProblemsController {

    private final ProblemService service;

    public ProblemsController(final ProblemService service) {
        super();

        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<? extends DataProblem> read(final Pagination pagination, final Sort sort) {
        return service.getAll(pagination, sort);
    }

}
