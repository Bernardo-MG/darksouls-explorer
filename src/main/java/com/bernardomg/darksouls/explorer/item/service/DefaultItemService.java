
package com.bernardomg.darksouls.explorer.item.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bernardomg.darksouls.explorer.item.domain.Item;
import com.bernardomg.darksouls.explorer.item.domain.ItemSource;
import com.bernardomg.darksouls.explorer.item.query.AllItemsQuery;
import com.bernardomg.darksouls.explorer.item.query.ItemQuery;
import com.bernardomg.darksouls.explorer.item.query.ItemSourcesQuery;
import com.bernardomg.darksouls.explorer.item.request.ItemRequest;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@Service
public final class DefaultItemService implements ItemService {

    private final QueryExecutor<String> queryExecutor;

    @Autowired
    public DefaultItemService(final QueryExecutor<String> queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<Item> getAll(final ItemRequest request,
            final Pagination pagination, final Sort sort) {
        final Query<List<Item>> query;
        final Collection<String> tags;

        tags = request.getSelectors()
            .stream()
            .map(String::toLowerCase)
            .map(StringUtils::capitalize)
            .collect(Collectors.toList());

        query = new AllItemsQuery(request.getName(), tags);

        return queryExecutor.fetch(query.getStatement(), query::getOutput,
            pagination, Arrays.asList(sort));
    }

    @Override
    public final Item getOne(final Long id) {
        final Map<String, Object> params;
        final Query<Item> query;

        query = new ItemQuery();

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor
            .fetchOne(query.getStatement(),
                (d) -> query.getOutput(Arrays.asList(d)), params)
            .orElse(null);
    }

    @Override
    public final PageIterable<ItemSource> getSources(final Long id,
            final Pagination pagination, final Sort sort) {
        final Map<String, Object> params;
        final Query<List<ItemSource>> query;

        query = new ItemSourcesQuery();

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetch(query.getStatement(), query::getOutput,
            params, pagination, Arrays.asList(sort));
    }

}
