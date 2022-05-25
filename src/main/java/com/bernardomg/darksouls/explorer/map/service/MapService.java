
package com.bernardomg.darksouls.explorer.map.service;

import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public interface MapService {

    public PageIterable<Map> getAll(final Pagination pagination,
            final Sort sort);

    public PageIterable<MapConnection>
            getAllConnections(final Pagination pagination, final Sort sort);

}
