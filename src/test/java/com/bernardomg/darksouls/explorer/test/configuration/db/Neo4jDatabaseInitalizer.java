
package com.bernardomg.darksouls.explorer.test.configuration.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.util.ResourceUtils;

public final class Neo4jDatabaseInitalizer implements DatabaseInitalizer {

    public Neo4jDatabaseInitalizer() {
        super();
    }

    @Override
    public final void initialize(final String user, final String password,
            final String url, final Iterable<String> files) {
        final AuthToken auth;

        auth = AuthTokens.basic(user, password);
        try (final Driver driver = GraphDatabase.driver(url, auth);
                final Session session = driver.session()) {
            for (final String file : files) {
                try {
                    loadFile(session, file);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final void loadFile(final Session session, final String path)
            throws IOException {
        final File file;

        file = ResourceUtils.getFile(path);
        try (final BufferedReader br = new BufferedReader(
            new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String instruction = line;
                session
                    .<Object> writeTransaction(work -> work.run(instruction));
            }
        }
    }

}
