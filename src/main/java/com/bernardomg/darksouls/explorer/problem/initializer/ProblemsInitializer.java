
package com.bernardomg.darksouls.explorer.problem.initializer;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.problem.service.ProblemService;

@Component
public final class ProblemsInitializer
        implements ApplicationListener<ContextRefreshedEvent> {

    private final ProblemService service;

    @Autowired
    public ProblemsInitializer(final ProblemService svc) {
        super();

        service = Objects.requireNonNull(svc);
    }

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        service.recollectAndRegister();
    }

}
