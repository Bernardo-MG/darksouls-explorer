
package com.bernardomg.darksouls.explorer.metadata.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.bernardomg.darksouls.explorer.metadata.query.AllTagsQuery;
import com.bernardomg.darksouls.explorer.persistence.Query;
import com.bernardomg.darksouls.explorer.persistence.QueryCommandExecutor;

@Component
public final class DefaultMetadataService implements MetadataService {

    private final QueryCommandExecutor queryExecutor;

    public DefaultMetadataService(final QueryCommandExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final Iterable<String> getTags(final String rootTag) {
        final Query<List<String>> query;

        query = new AllTagsQuery(rootTag);

        return queryExecutor.fetch(query);
    }

}
