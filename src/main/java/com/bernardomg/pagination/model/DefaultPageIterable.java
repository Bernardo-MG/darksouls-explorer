
package com.bernardomg.pagination.model;

import java.util.Collections;
import java.util.Iterator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultPageIterable<T> implements PageIterable<T> {

    /**
     * Actual content.
     */
    @NonNull
    private Iterable<T> content        = Collections.emptyList();

    /**
     * Number of elements in the page.
     */
    private Integer     elementsInPage = 0;

    /**
     * Flags this is as the first page.
     */
    private Boolean     first          = false;

    /**
     * Flags this is as the last page.
     */
    private Boolean     last           = false;

    /**
     * Number of this page.
     */
    private Integer     pageNumber     = 0;

    /**
     * Size of this page.
     */
    private Integer     size           = 0;

    /**
     * Total number of elements among all the pages.
     */
    private Long        totalElements  = 0l;

    /**
     * Total number of pages.
     */
    private Integer     totalPages     = 0;

    @Override
    public Boolean isFirst() {
        return first;
    }

    @Override
    public Boolean isLast() {
        return last;
    }

    @Override
    public final Iterator<T> iterator() {
        return content.iterator();
    }

}
