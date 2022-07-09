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

/**
 * Sorted data request.
 * <p>
 * Includes a flag to mark is this request is actually sorted, to ease disabled sorting.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface Sort {

    /**
     * Creates a {@code Sort} which represents disabled sorting.
     *
     * @return a disabled {@code Sort}
     */
    public static Sort disabled() {
        return DisabledSort.INSTANCE;
    }

    /**
     * Creates a {@code Sort} for the property and direction.
     *
     * @param property
     *            property to sort
     * @param direction
     *            sorting direction
     * @return a {@code Sort} for the arguments
     */
    public static Sort of(final String property, final Direction direction) {
        return new ImmutableSort(property, direction);
    }

    /**
     * Direction in which the data will be sorted.
     *
     * @return the direction for sorting
     */
    public Direction getDirection();

    /**
     * Property to sort.
     *
     * @return the property to sort
     */
    public String getProperty();

    /**
     * Flags if sorting should be applied. This makes it easier disabling sorting.
     *
     * @return {@code true} if this is sorted, {@code false} otherwise
     */
    public Boolean getSorted();

}
