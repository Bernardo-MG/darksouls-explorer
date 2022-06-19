
package com.bernardomg.test.unit.pagination.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.bernardomg.pagination.model.DefaultPagination;
import com.bernardomg.pagination.model.DefaultSort;
import com.bernardomg.pagination.model.Direction;
import com.bernardomg.pagination.model.DisabledPagination;
import com.bernardomg.pagination.model.DisabledSort;
import com.bernardomg.pagination.model.Pagination;
import com.bernardomg.pagination.model.Sort;
import com.bernardomg.pagination.utils.Paginations;

@DisplayName("Pagination utils - Pagination to Spring model")
public class TestPaginationsToSpring {

    public TestPaginationsToSpring() {
        super();
    }

    @Test
    @DisplayName("With disabled pagination the result is disabled")
    public void testPagination_Disabled_Unpaged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DisabledPagination();
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("With the smalles pagination values the result is enabled")
    public void testPagination_Minimal_Paged() {
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
    public void testPagination_Negative_Unpaged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(-1, -1);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for the first page")
    public void testPagination_Values_FirstPage() {
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
    public void testPagination_Values_SecondPage() {
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
    public void testPagination_Zeros_Unpaged() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DefaultPagination(0, 0);
        sort = new DisabledSort();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("With disabled pagination the result sort is disabled")
    public void testSort_DisabledPagination_Sorted() {
        final Pagination pagination;
        final Sort sort;
        final Pageable result;

        pagination = new DisabledPagination();
        sort = new DefaultSort("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("With disabled sort the result sort is disabled")
    public void testSort_DisabledSort_Unsorted() {
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
    @DisplayName("With valid sort the result sort is enabled")
    public void testSort_Sorted() {
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
    public void testSort_Values_Ascending() {
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
    public void testSort_Values_Descending() {
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
        Assertions.assertEquals(
            org.springframework.data.domain.Sort.Direction.DESC,
            result.getSort()
                .iterator()
                .next()
                .getDirection());
    }

}
