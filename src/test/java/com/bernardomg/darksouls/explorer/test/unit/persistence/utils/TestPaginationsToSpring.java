
package com.bernardomg.darksouls.explorer.test.unit.persistence.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.bernardomg.darksouls.explorer.persistence.model.DefaultPagination;
import com.bernardomg.darksouls.explorer.persistence.model.DefaultSort;
import com.bernardomg.darksouls.explorer.persistence.model.Direction;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledPagination;
import com.bernardomg.darksouls.explorer.persistence.model.DisabledSort;
import com.bernardomg.darksouls.explorer.persistence.model.Pagination;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.persistence.utils.Paginations;

@DisplayName("Pagination utils - Pagination to Spring model")
public class TestPaginationsToSpring {

    public TestPaginationsToSpring() {
        super();
    }

    @Test
    @DisplayName("With disabled pagination the result is disabled")
    public void testDisabled_Unpaged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DisabledPagination();
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("With disabled sort the result sort is disabled")
    public void testDisabledSort_Unsorted() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 10);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("With the smalles pagination values the result is enabled")
    public void testMinimal_Paged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(1, 1);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("With negative pagination values the result is disabled")
    public void testNegative_Unpaged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(-1, -1);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("With disabled pagination the result sort is disabled")
    public void testSorted_DisabledPagination_NotSorted() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DisabledPagination();
        sort = new DefaultSort("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("With valid sort the result sort is enabled")
    public void testSorted_Sorted() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 10);
        sort = new DefaultSort("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("Applies the correct values for ascending order")
    public void testSortedValues_Ascending() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 10);
        sort = new DefaultSort("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(1, result.getSort()
            .toList()
            .size());
        Assertions.assertEquals("field", result.getSort()
            .iterator()
            .next()
            .getProperty());
        Assertions
            .assertEquals(org.springframework.data.domain.Sort.Direction.ASC,
                result.getSort()
                    .iterator()
                    .next()
                    .getDirection());
    }

    @Test
    @DisplayName("Applies the correct values for descending order")
    public void testSortedValues_Descending() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 10);
        sort = new DefaultSort("field", Direction.DESC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(1, result.getSort()
            .toList()
            .size());
        Assertions.assertEquals("field", result.getSort()
            .iterator()
            .next()
            .getProperty());
        Assertions
            .assertEquals(org.springframework.data.domain.Sort.Direction.ASC,
                result.getSort()
                    .iterator()
                    .next()
                    .getDirection());
    }

    @Test
    @DisplayName("Applies the correct values for the first page")
    public void testValues_FirstPage() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 10);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(0, result.getOffset());
        Assertions.assertEquals(0, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("Applies the correct values for the second page")
    public void testValues_SecondPage() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(1, 10);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(10, result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("With zero pagination values the result is disabled")
    public void testZeros_Unpaged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 0);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

}
