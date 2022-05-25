
package com.bernardomg.darksouls.explorer.persistence.utils;

import java.util.Map;

/**
 * For some reason the maps returned by Neo4J always say they contain values,
 * even when they don't, breaking default value handling. This class avoids
 * working with nulls.
 * 
 * @author Bernardo
 *
 */
public final class Maps {

    public static final <T> T getOrDefault(final Map<String, Object> map,
            final String key, final T dflt) {
        final T result;
        final T value;

        if (map.containsKey(key)) {
            value = (T) map.get(key);
            if (value == null) {
                result = dflt;
            } else {
                result = value;
            }
        } else {
            result = dflt;
        }

        return result;
    }

    private Maps() {
        super();
    }

}
