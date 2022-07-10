
package com.bernardomg.darksouls.explorer.initialize.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.darksouls.explorer.initialize.listener.ProblemsInitializerListener;
import com.bernardomg.darksouls.explorer.problem.service.ProblemService;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "problem", havingValue = "true")
public class ProblemInitializerConfig {

    public ProblemInitializerConfig() {
        super();
    }

    @Bean("problemsInitializer")
    public ProblemsInitializerListener getProblemsInitializer(final ProblemService svc) {
        return new ProblemsInitializerListener(svc);
    }

}
