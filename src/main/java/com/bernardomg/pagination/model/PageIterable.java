/**
 * Copyright 2021 the original author or authors
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
 * Paged iterable.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 * @param <T>
 *            the type of elements returned by the iterator
 */
public interface PageIterable<T> extends Iterable<T> {

    /**
     * Actual content.
     *
     * @return content wrapped by the page
     */
    public Iterable<T> getContent();

    /**
     * Number of elements in the page.
     *
     * @return number of elements
     */
    public Integer getElementsInPage();

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

    /**
     * Flags this is as the first page.
     *
     * @return {@code true} if this is the first page, {@code false} otherwise
     */
    public Boolean isFirst();

    /**
     * Flags this is as the last page.
     *
     * @return {@code true} if this is the last page, {@code false} otherwise
     */
    public Boolean isLast();

}
