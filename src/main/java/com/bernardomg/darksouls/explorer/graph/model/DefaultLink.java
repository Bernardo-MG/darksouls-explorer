
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
public final class DefaultLink implements Link {

    @NonNull
    private final Map<String, Object> attributes  = new HashMap<>();

    @NonNull
    private Long                      source      = 0l;

    @NonNull
    private String                    sourceLabel = "";

    @NonNull
    private Long                      target      = 0l;

    @NonNull
    private String                    targetLabel = "";

    @NonNull
    private String                    type        = "";

    @Override
    public final void addAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

}
