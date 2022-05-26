
package com.bernardomg.darksouls.explorer.persistence.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.persistence.model.DefaultPageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Direction;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;

public final class Paginations {

    public static final <T> PageIterable<T> fromSpring(final Page<T> page) {
        final DefaultPageIterable<T> result;

        result = new DefaultPageIterable<>();
        result.setContent(page.getContent());
        result.setElementsInPage(page.getNumberOfElements());
        result.setFirst(page.isFirst());
        result.setLast(page.isLast());
        result.setPageNumber(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalElements(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());

        return result;
    }

    public static final Pageable toSpring(final Pagination pagination,
            final Sort sort) {
        final Pageable pageable;

        if (pagination.getPaged()) {
            pageable = PageRequest.of(pagination.getPage(),
                pagination.getSize());
        } else if (pagination.getPaged()) {
            pageable = PageRequest.of(pagination.getPage(),
                pagination.getSize());
        } else if (sort.getSorted()) {
            pageable = PageRequest.of(0, 10,
                toSpringDirection(sort.getDirection()), sort.getProperty());
        } else {
            pageable = Pageable.unpaged();
        }

        return pageable;
    }

    private static final org.springframework.data.domain.Sort.Direction
            toSpringDirection(final Direction direction) {
        final org.springframework.data.domain.Sort.Direction result;

        if (Direction.ASC.equals(direction)) {
            result = org.springframework.data.domain.Sort.Direction.ASC;
        } else {
            result = org.springframework.data.domain.Sort.Direction.DESC;
        }

        return result;
    }

    private Paginations() {
        super();
    }

}
