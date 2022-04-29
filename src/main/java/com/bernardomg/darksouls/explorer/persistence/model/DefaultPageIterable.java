
package com.bernardomg.darksouls.explorer.persistence.model;

import java.util.Iterator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public final class DefaultPageIterable<T> implements PageIterable<T> {

    @NonNull
    private Iterable<T> content;

    @NonNull
    private Integer     elementsInPage;

    @NonNull
    private Boolean     first;

    @NonNull
    private Boolean     last;

    @NonNull
    private Integer     pageNumber;

    @NonNull
    private Integer     size;

    @NonNull
    private Long        totalElements;

    @NonNull
    private Integer     totalPages;

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
