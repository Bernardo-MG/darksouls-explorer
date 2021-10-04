
package com.bernardomg.darksouls.explorer.test.configuration.db;

import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.utility.DockerImageName;

public final class ContainerFactory {

    @SuppressWarnings("resource")
    public static final Neo4jContainer<?> getNeo4jContainer() {
        return new Neo4jContainer<>(
                DockerImageName.parse("neo4j").withTag("3.5.27"))
                        .withReuse(true);
    }

    public ContainerFactory() {
        super();
    }

}
