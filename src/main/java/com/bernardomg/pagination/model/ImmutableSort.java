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
 * Immutable implementation of the sorted data request.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public final class ImmutableSort implements Sort {

    /**
     * Direction in which the data will be sorted.
     */
    @NonNull
    private final Direction direction;

    /**
     * Property to sort.
     */
    @NonNull
    private final String    property;

    /**
     * Always sorted.
     */
    private final Boolean   sorted = true;

    /**
     * Builds a sort request with the specified data.
     *
     * @param prop
     *            property to sort
     * @param dir
     *            sort direction
     */
    public ImmutableSort(@NonNull final String prop, @NonNull final Direction dir) {
        super();

        property = prop;
        direction = dir;
    }

}
