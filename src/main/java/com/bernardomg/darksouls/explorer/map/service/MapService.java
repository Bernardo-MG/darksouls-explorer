
package com.bernardomg.darksouls.explorer.map.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.map.domain.Map;

public interface MapService {

    public Page<Map> getAll(final Pageable page);

}
