
package com.bernardomg.darksouls.explorer.test.configuration.context;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.Neo4jContainer;

public final class Neo4jApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final Neo4jContainer<?> neo4jContainer;

    public Neo4jApplicationContextInitializer(
            final Neo4jContainer<?> container) {
        super();

        neo4jContainer = container;
    }

    @Override
    public void initialize(
            final ConfigurableApplicationContext configurableApplicationContext) {

        neo4jContainer.addExposedPorts(7687);
        TestPropertyValues
                .of("spring.neo4j.uri=" + neo4jContainer.getBoltUrl(),
                        "spring.neo4j.authentication.username=neo4j",
                        "spring.neo4j.authentication.password="
                                + neo4jContainer.getAdminPassword())
                .applyTo(configurableApplicationContext.getEnvironment());
    }

}
