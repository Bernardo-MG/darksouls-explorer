
package com.bernardomg.darksouls.explorer.map.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.map.domain.Map;
import com.bernardomg.darksouls.explorer.map.domain.MapConnection;

public interface MapQueries {

    public Page<Map> findAll(final Pageable page);

    public Page<MapConnection> findAllConnections(final Pageable page);

}
