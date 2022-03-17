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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bernardomg.darksouls.explorer.persistence.DefaultDslQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.DefaultQueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.TextQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.DslQueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryCommandExecutor;
import com.bernardomg.darksouls.explorer.persistence.QueryExecutor;

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

    @Bean("queryCommandExecutor")
    public QueryCommandExecutor getQueryCommandExecutor(
            final QueryExecutor exec, final DslQueryExecutor dslExec) {
        return new DefaultQueryCommandExecutor(exec, dslExec);
    }

    @Bean("dslQueryExecutor")
    public DslQueryExecutor getDslQueryExecutor(final Neo4jClient clnt) {
        return new DefaultDslQueryExecutor(clnt);
    }

    @Bean("queryExecutor")
    public QueryExecutor getQueryExecutor(final Neo4jClient clnt) {
        return new TextQueryExecutor(clnt);
    }

}
