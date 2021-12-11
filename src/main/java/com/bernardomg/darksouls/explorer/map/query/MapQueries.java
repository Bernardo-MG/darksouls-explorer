
package com.bernardomg.darksouls.explorer.map.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.map.domain.Map;

public interface MapQueries {

    public Page<Map> findAll(final Pageable page);

}
