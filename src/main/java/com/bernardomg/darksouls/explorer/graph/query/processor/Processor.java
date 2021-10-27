
package com.bernardomg.darksouls.explorer.graph.query.processor;

import java.util.Collection;
import java.util.Map;

public interface Processor<T> {

    public T process(final Collection<Map<String, Object>> rows);

}
