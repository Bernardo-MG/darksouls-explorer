
package com.bernardomg.darksouls.explorer.persistence;

import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.StatementBuilder.BuildableStatement;

public interface DslQuery<S>
        extends Query<S, BuildableStatement<ResultStatement>> {

}
