
package com.bernardomg.darksouls.explorer.persistence.utils;

import org.springframework.data.domain.Page;

import com.bernardomg.darksouls.explorer.persistence.model.DefaultPageIterable;
import com.bernardomg.darksouls.explorer.persistence.model.PageIterable;

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

    private Paginations() {
        super();
    }

}
