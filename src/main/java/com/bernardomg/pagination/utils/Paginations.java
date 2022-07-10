/**
 * Copyright 2021-2022 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.pagination.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bernardomg.pagination.model.DefaultPageIterable;
import com.bernardomg.pagination.model.Direction;
import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;

/**
 * Pagination utility class.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
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

    public static final Pageable toSpring(final Pagination pagination, final Sort sort) {
        final Pageable                                       pageable;
        final org.springframework.data.domain.Sort.Direction direction;
        final Boolean                                        paged;
        final Integer                                        size;

        paged = (pagination.getPaged()) && (pagination.getPage() >= 0);

        if (pagination.getSize() > 0) {
            size = pagination.getSize();
        } else {
            size = Pagination.DEFAULT_SIZE;
        }

        if ((paged) && (sort.getSorted())) {
            // Paged and sorted
            direction = toSpringDirection(sort.getDirection());
            pageable = PageRequest.of(pagination.getPage(), size, direction, sort.getProperty());
        } else if (paged) {
            // Only paged
            pageable = PageRequest.of(pagination.getPage(), size);
        } else if (sort.getSorted()) {
            // Only sorted
            direction = toSpringDirection(sort.getDirection());
            pageable = PageRequest.of(0, Pagination.DEFAULT_SIZE, direction, sort.getProperty());
        } else {
            // Not paged nor sorted
            pageable = Pageable.unpaged();
        }

        return pageable;
    }

    private static final org.springframework.data.domain.Sort.Direction toSpringDirection(final Direction direction) {
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
