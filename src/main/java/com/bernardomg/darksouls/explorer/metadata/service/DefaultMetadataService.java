
package com.bernardomg.darksouls.explorer.metadata.service;

import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.metadata.query.MetadataQueries;

@Component
public final class DefaultMetadataService implements MetadataService {

    private final MetadataQueries queries;

    public DefaultMetadataService(final MetadataQueries queries) {
        super();

        this.queries = queries;
    }

    @Override
    public final Iterable<String> getTags(final String rootTag) {
        return queries.getTags(rootTag);
    }

}
