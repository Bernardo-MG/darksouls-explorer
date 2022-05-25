
package com.bernardomg.darksouls.explorer.test.unit.request.argument;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.darksouls.explorer.persistence.model.Direction;
import com.bernardomg.darksouls.explorer.persistence.model.Sort;
import com.bernardomg.darksouls.explorer.request.argument.SortArgumentResolver;

@DisplayName("Sort argument resolver")
public class TestSortArgumentResolver {

    private final HandlerMethodArgumentResolver resolver = new SortArgumentResolver();

    public TestSortArgumentResolver() {
        super();
    }

    @Test
    @DisplayName("Returns the default direction when an invalid direction is received")
    public void testResolve_FullSort_InvalidDirection_Values()
            throws Exception {
        final MethodParameter parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest webRequest;
        final WebDataBinderFactory binderFactory;
        final Sort sort;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("sort"))
            .thenReturn("field,abc");

        sort = (Sort) resolver.resolveArgument(parameter, mavContainer,
            webRequest, binderFactory);

        Assertions.assertEquals("field", sort.getProperty());
        Assertions.assertEquals(Direction.ASC, sort.getDirection());
    }

    @Test
    @DisplayName("The sort is sorted when receiving all values")
    public void testResolve_FullSort_Sorted() throws Exception {
        final MethodParameter parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest webRequest;
        final WebDataBinderFactory binderFactory;
        final Sort sort;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("sort"))
            .thenReturn("field,asc");

        sort = (Sort) resolver.resolveArgument(parameter, mavContainer,
            webRequest, binderFactory);

        Assertions.assertTrue(sort.getSorted());
    }

    @Test
    @DisplayName("Returns order when receiving all the arguments")
    public void testResolve_FullSort_Values() throws Exception {
        final MethodParameter parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest webRequest;
        final WebDataBinderFactory binderFactory;
        final Sort sort;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("sort"))
            .thenReturn("field,asc");

        sort = (Sort) resolver.resolveArgument(parameter, mavContainer,
            webRequest, binderFactory);

        Assertions.assertEquals("field", sort.getProperty());
        Assertions.assertEquals(Direction.ASC, sort.getDirection());
    }

    @Test
    @DisplayName("Returns the default direction when no direction is received")
    public void testResolve_NoDirection_DefaultDirection() throws Exception {
        final MethodParameter parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest webRequest;
        final WebDataBinderFactory binderFactory;
        final Sort sort;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("sort"))
            .thenReturn("field");

        sort = (Sort) resolver.resolveArgument(parameter, mavContainer,
            webRequest, binderFactory);

        Assertions.assertEquals(Direction.ASC, sort.getDirection());
    }

    @Test
    @DisplayName("The sort is sorted when receiving no direction")
    public void testResolve_NoDirection_Sorted() throws Exception {
        final MethodParameter parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest webRequest;
        final WebDataBinderFactory binderFactory;
        final Sort sort;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("sort"))
            .thenReturn("field");

        sort = (Sort) resolver.resolveArgument(parameter, mavContainer,
            webRequest, binderFactory);

        Assertions.assertTrue(sort.getSorted());
    }

    @Test
    @DisplayName("The pagination is not sorted when receiving no parameters")
    public void testResolve_NoSort_NotSorted() throws Exception {
        final MethodParameter parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest webRequest;
        final WebDataBinderFactory binderFactory;
        final Sort sort;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        sort = (Sort) resolver.resolveArgument(parameter, mavContainer,
            webRequest, binderFactory);

        Assertions.assertFalse(sort.getSorted());
    }

}
