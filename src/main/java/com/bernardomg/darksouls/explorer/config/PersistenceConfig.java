/**
 * Copyright 2021 the original author or authors
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

package com.bernardomg.darksouls.explorer.config;

import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bernardomg.darksouls.explorer.persistence.DefaultQueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.DslQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.TextQueryExecutor;

/**
 * Persistence configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    /**
     * Default constructor.
     */
    public PersistenceConfig() {
        super();
    }

    @Bean("dslQueryExecutor")
    public DslQueryExecutor getDslQueryExecutor(final Neo4jClient clnt) {
        return new DslQueryExecutor(clnt);
    }

    @Bean("queryCommandExecutor")
    public QueryCommandExecutor getQueryCommandExecutor(
            final QueryExecutor<String> exec,
            final QueryExecutor<BuildableStatement<ResultStatement>> dslExec) {
        return new DefaultQueryCommandExecutor(exec, dslExec);
    }

    @Bean("queryExecutor")
    public QueryExecutor getQueryExecutor(final Neo4jClient clnt) {
        return new TextQueryExecutor(clnt);
    }

}
