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

package com.bernardomg.pagination.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Immutable implementation of the paginated data request.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class ImmutablePagination implements Pagination {

    /**
     * Page index to read.
     */
    @NonNull
    private final Integer page;

    /**
     * Always paged.
     */
    @NonNull
    private final Boolean paged = true;

    /**
     * Number of elements to read per page.
     */
    @NonNull
    private final Integer size;

    /**
     * Builds a pagination request for the specified page and default size.
     *
     * @param pg
     *            page index
     */
    public ImmutablePagination(@NonNull final Integer pg) {
        super();

        page = pg;
        size = Pagination.DEFAULT_SIZE;
    }

    /**
     * Builds a pagination request with the specified data.
     *
     * @param pg
     *            page index
     * @param sz
     *            page size
     */
    public ImmutablePagination(@NonNull final Integer pg, @NonNull final Integer sz) {
        super();

        page = pg;
        size = sz;
    }

}
