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
 * Disabled paginated data request. This serves as a null object to disable pagination.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class DisabledPagination implements Pagination {

    /**
     * Singleton for disabled pagination.
     */
    public static final Pagination INSTANCE = new DisabledPagination();

    /**
     * Default page.
     */
    @NonNull
    private final Integer    page     = -1;

    /**
     * Disabled pagination flag.
     */
    @NonNull
    private final Boolean    paged    = false;

    /**
     * Default size.
     */
    @NonNull
    private final Integer    size     = -1;

}
