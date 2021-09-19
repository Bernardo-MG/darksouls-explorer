
package com.bernardomg.darksouls.explorer.graph.query.processor;

import org.neo4j.driver.Result;

public interface Processor<T> {

    public T process(final Result rows);

}
