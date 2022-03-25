
package com.bernardomg.darksouls.explorer.persistence;

import java.util.Map;

public interface Query<T> {

    public T getOutput(final Iterable<Map<String, Object>> record);

    public String getStatement();

}
