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

package com.bernardomg.response.model;

/**
 * Paginated response to the frontend.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 * @param <T>
 *            response content type
 */
public interface PaginatedResponse<T> extends Response<T> {

    /**
     * Number of elements in the page.
     *
     * @return number of elements
     */
    public Integer getElementsInPage();

    /**
     * Flags this is as the first page.
     *
     * @return {@code true} if this is the first page, {@code false} otherwise
     */
    public Boolean getFirst();

    /**
     * Flags this is as the last page.
     *
     * @return {@code true} if this is the last page, {@code false} otherwise
     */
    public Boolean getLast();

    /**
     * Number of this page.
     *
     * @return the number of this page
     */
    public Integer getPageNumber();

    /**
     * Size of this page.
     *
     * @return the size of this page.
     */
    public Integer getSize();

    /**
     * Total number of elements among all the pages.
     *
     * @return the total number of elements
     */
    public Long getTotalElements();

    /**
     * Total number of pages.
     *
     * @return the total number of pages
     */
    public Integer getTotalPages();

}
