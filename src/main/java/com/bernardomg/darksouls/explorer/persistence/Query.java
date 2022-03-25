
package com.bernardomg.darksouls.explorer.persistence;

import java.util.Map;

public interface Query<T, S> {

    public T getOutput(final Iterable<Map<String, Object>> record);

    public S getStatement();

}
