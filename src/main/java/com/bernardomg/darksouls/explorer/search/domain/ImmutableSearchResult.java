
package com.bernardomg.darksouls.explorer.search.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableSearchResult implements SearchResult {

    @NonNull
    private final Long   id;

    @NonNull
    private final String name;

    @NonNull
    private final String path;

    public ImmutableSearchResult(@NonNull final Long id, @NonNull final String name, @NonNull final String path) {
        super();

        this.id = id;
        this.name = name;
        this.path = path;
    }

}
