
package com.bernardomg.darksouls.explorer.initialize.listener;

import java.util.Objects;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.bernardomg.darksouls.explorer.problem.service.ProblemService;

public final class ProblemsInitializerListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ProblemService service;

    public ProblemsInitializerListener(final ProblemService svc) {
        super();

        service = Objects.requireNonNull(svc);
    }

    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        service.recollectAndRegister();
    }

}
