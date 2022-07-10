
package com.bernardomg.pagination.test.unit.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.bernardomg.pagination.model.Direction;
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
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.disabled();
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for the first page")
    public void testPagination_FirstPage_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(0, result.getOffset());
        Assertions.assertEquals(0, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("With the smalles pagination values the result is enabled")
    public void testPagination_Minimal_Paged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("With negative pagination page the result is disabled")
    public void testPagination_NegativePage_Unpaged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(-1, 1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.isPaged());
    }

    @Test
    @DisplayName("With negative pagination size the result is enabled")
    public void testPagination_NegativeSize_Paged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, -1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for negative pagination size")
    public void testPagination_NegativeSize_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(1, -1);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(Pagination.DEFAULT_SIZE.longValue(), result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, result.getPageSize());
    }

    @Test
    @DisplayName("Applies the correct values for the second page")
    public void testPagination_SecondPage_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(1, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(10, result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("With zero pagination size the result is enabled")
    public void testPagination_ZeroSize_Paged() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 0);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.isPaged());
    }

    @Test
    @DisplayName("Applies the correct values for zero pagination size")
    public void testPagination_ZeroSize_Values() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(1, 0);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(Pagination.DEFAULT_SIZE.longValue(), result.getOffset());
        Assertions.assertEquals(1, result.getPageNumber());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, result.getPageSize());
    }

    @Test
    @DisplayName("With an ascending sort the resulting sort is enabled")
    public void testSort_Ascending_Sorted() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.of("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("Applies the correct values for ascending order")
    public void testSort_Ascending_SortValues() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.of("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(1, result.getSort()
            .toList()
            .size());
        Assertions.assertEquals("field", result.getSort()
            .iterator()
            .next()
            .getProperty());
        Assertions.assertEquals(org.springframework.data.domain.Sort.Direction.ASC, result.getSort()
            .iterator()
            .next()
            .getDirection());
    }

    @Test
    @DisplayName("With a descending sort the resulting sort is enabled")
    public void testSort_Descending_Sorted() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.of("field", Direction.DESC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("Applies the correct values for descending order")
    public void testSort_Descending_SortValues() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.of("field", Direction.DESC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(1, result.getSort()
            .toList()
            .size());
        Assertions.assertEquals("field", result.getSort()
            .iterator()
            .next()
            .getProperty());
        Assertions.assertEquals(org.springframework.data.domain.Sort.Direction.DESC, result.getSort()
            .iterator()
            .next()
            .getDirection());
    }

    @Test
    @DisplayName("Applies the correct values for a disabled pagination with sort")
    public void testSort_DisabledPagination_PaginationValues() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.disabled();
        sort = Sort.of("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(0, result.getOffset());
        Assertions.assertEquals(0, result.getPageNumber());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, result.getPageSize());
    }

    @Test
    @DisplayName("With disabled pagination the resulting sort is disabled")
    public void testSort_DisabledPagination_Sorted() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.disabled();
        sort = Sort.of("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertTrue(result.getSort()
            .isSorted());
    }

    @Test
    @DisplayName("With disabled pagination the resulting sort contains all the data")
    public void testSort_DisabledPagination_SortValues() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.disabled();
        sort = Sort.of("field", Direction.ASC);

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(1, result.getSort()
            .toList()
            .size());
        Assertions.assertEquals("field", result.getSort()
            .iterator()
            .next()
            .getProperty());
        Assertions.assertEquals(org.springframework.data.domain.Sort.Direction.ASC, result.getSort()
            .iterator()
            .next()
            .getDirection());
    }

    @Test
    @DisplayName("Applies the correct values for a disabled sort with pagination")
    public void testSort_DisabledSort_PaginationValues() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(0, result.getOffset());
        Assertions.assertEquals(0, result.getPageNumber());
        Assertions.assertEquals(10, result.getPageSize());
    }

    @Test
    @DisplayName("With disabled sort the resulting sort contains no data")
    public void testSort_DisabledSort_SortValues() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertEquals(0, result.getSort()
            .toList()
            .size());
    }

    @Test
    @DisplayName("With disabled sort the resulting sort is disabled")
    public void testSort_DisabledSort_Unsorted() {
        final Pagination pagination;
        final Sort       sort;
        final Pageable   result;

        pagination = Pagination.of(0, 10);
        sort = Sort.disabled();

        result = Paginations.toSpring(pagination, sort);

        Assertions.assertFalse(result.getSort()
            .isSorted());
    }

}
