
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultLink implements Link {

    @NonNull
    private final Map<String, Object> attributes = new HashMap<>();

    @NonNull
    private String                    source     = "";

    @NonNull
    private Long                      sourceId   = 0l;

    @NonNull
    private String                    target     = "";

    @NonNull
    private Long                      targetId   = 0l;

    @NonNull
    private String                    type       = "";

    @Override
    public final void addAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

}
