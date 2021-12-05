
package com.bernardomg.darksouls.explorer.test.configuration.context;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.Neo4jContainer;

public final class Neo4jApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final Neo4jContainer<?> dbContainer;

    public Neo4jApplicationContextInitializer(
            final Neo4jContainer<?> container) {
        super();

        dbContainer = container;
    }

    @Override
    public void initialize(
            final ConfigurableApplicationContext configurableApplicationContext) {

        dbContainer.addExposedPorts(7687);
        TestPropertyValues
            .of("spring.neo4j.uri=" + dbContainer.getBoltUrl(),
                "spring.neo4j.authentication.username=neo4j",
                "spring.neo4j.authentication.password="
                        + dbContainer.getAdminPassword())
            .applyTo(configurableApplicationContext.getEnvironment());
    }

}
