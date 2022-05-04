
package com.bernardomg.darksouls.explorer.item.spell.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.darksouls.explorer.item.armor.domain.request.ArmorRequest;
import com.bernardomg.darksouls.explorer.item.spell.domain.Spell;
import com.bernardomg.darksouls.explorer.item.spell.query.SpellsQuery;
import com.bernardomg.darksouls.explorer.persistence.executor.QueryExecutor;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Query;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

@Service
public final class DefaultSpellService implements SpellService {

    private final Query<Spell>  allQuery = new SpellsQuery();

    private final QueryExecutor queryExecutor;

    @Autowired
    public DefaultSpellService(final QueryExecutor queryExec) {
        super();

        queryExecutor = Objects.requireNonNull(queryExec);
    }

    @Override
    public final PageIterable<Spell> getAll(final ArmorRequest request,
            final Pagination pagination, final Sort sort) {
        return queryExecutor.fetch(allQuery::getStatement, allQuery::getOutput,
            pagination, Arrays.asList(sort));
    }

    @Override
    public final Optional<Spell> getOne(final Long id) {
        final Map<String, Object> params;

        params = new HashMap<>();
        params.put("id", id);

        return queryExecutor.fetchOne(allQuery::getStatement,
            allQuery::getOutput, params);
    }

}
