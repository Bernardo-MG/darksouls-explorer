/**
 * Copyright 2020 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.darksouls.explorer.test.integration.common;

import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;

public final class Neo4jTestData {

    public static Neo4j getDescription() {
        return Neo4jBuilders.newInProcessBuilder().withDisabledServer()
                .withFixture("CREATE ({name: 'Source'});")
                .withFixture(
                        "CREATE ({name: 'Target', description: 'line1|line2'});")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);")
                .build();
    }

    public static Neo4j getMultiple() {
        return Neo4jBuilders.newInProcessBuilder().withDisabledServer()
                .withFixture("CREATE ({name: 'Source'});")
                .withFixture("CREATE ({name: 'Target'});")
                .withFixture("CREATE ({name: 'Another'});")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Another'}) MERGE (n)-[:RELATIONSHIP]->(m);")
                .build();
    }

    public static Neo4j getSimple() {
        return Neo4jBuilders.newInProcessBuilder().withDisabledServer()
                .withFixture("CREATE ({name: 'Source'});")
                .withFixture("CREATE ({name: 'Target'});")
                .withFixture(
                        "MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);")
                .build();
    }

    /**
     * Default constructor.
     */
    public Neo4jTestData() {
        super();
    }

}
