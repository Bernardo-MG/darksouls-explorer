
package com.bernardomg.darksouls.explorer.persistence;

import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;

public interface DslQuery<T, S>
        extends Query<T, S, BuildableStatement<ResultStatement>> {

}
