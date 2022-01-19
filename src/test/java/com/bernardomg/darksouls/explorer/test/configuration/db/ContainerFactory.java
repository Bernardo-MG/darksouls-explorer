
package com.bernardomg.darksouls.explorer.test.configuration.db;

import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.utility.DockerImageName;

public final class ContainerFactory {

    public static final Neo4jContainer<?> getNeo4jContainer() {
        final Neo4jContainer<?> container;

        container = new Neo4jContainer<>(DockerImageName.parse("neo4j")
            .withTag("4.4.3"));
        container.withReuse(true);
        container.addEnv("NEO4JLABS_PLUGINS", "[\"apoc\"]");

        return container;
    }

    public ContainerFactory() {
        super();
    }

}
