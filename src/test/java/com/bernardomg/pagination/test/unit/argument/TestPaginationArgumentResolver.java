
package com.bernardomg.pagination.test.unit.argument;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.pagination.argument.PaginationArgumentResolver;
import com.bernardomg.pagination.model.Pagination;

@DisplayName("Pagination argument resolver")
public class TestPaginationArgumentResolver {

    private final HandlerMethodArgumentResolver resolver = new PaginationArgumentResolver();

    public TestPaginationArgumentResolver() {
        super();
    }

    @Test
    @DisplayName("The pagination can be disabled")
    public void testResolve_Disabled_NotPaged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("10");
        Mockito.when(webRequest.getParameter("paged"))
            .thenReturn("false");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertFalse(pagination.getPaged());
    }

    @Test
    @DisplayName("The pagination is paged when receiving all values")
    public void testResolve_FullPagination_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("10");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

    @Test
    @DisplayName("Returns pagination when receiving all the arguments")
    public void testResolve_FullPagination_Values() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("10");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(1, pagination.getPage());
        Assertions.assertEquals(10, pagination.getSize());
    }

    @Test
    @DisplayName("The pagination is paged when receiving a negative page")
    public void testResolve_NegativePage_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("-1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("1");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

    @Test
    @DisplayName("Returns pagination when receiving a negative page")
    public void testResolve_NegativePage_Values() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("-1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("1");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(-1, pagination.getPage());
        Assertions.assertEquals(1, pagination.getSize());
    }

    @Test
    @DisplayName("Returns pagination with default size when receiving a negative size")
    public void testResolve_NegativeSize_DefaultValues() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("-1");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(1, pagination.getPage());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, pagination.getSize());
    }

    @Test
    @DisplayName("The pagination is paged when receiving a negative size")
    public void testResolve_NegativeSize_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("-1");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

    @Test
    @DisplayName("The pagination is paged when receiving no pagination parameter")
    public void testResolve_NoPagination_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("0");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

    @Test
    @DisplayName("Returns pagination when receiving no pagination parameter")
    public void testResolve_NoPagination_Values() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("10");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(0, pagination.getPage());
        Assertions.assertEquals(10, pagination.getSize());
    }

    @Test
    @DisplayName("The pagination is paged when receiving no parameters")
    public void testResolve_NoParameters_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

    @Test
    @DisplayName("Returns pagination when receiving no parameters")
    public void testResolve_NoParameters_Values() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(0, pagination.getPage());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, pagination.getSize());
    }

    @Test
    @DisplayName("Returns the default size when no size is received")
    public void testResolve_NoSize_DefaultSize() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(20, pagination.getSize());
    }

    @Test
    @DisplayName("The pagination is paged when receiving no size")
    public void testResolve_NoSize_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

    @Test
    @DisplayName("Returns pagination with default size when receiving a size value of zero")
    public void testResolve_ZeroSize_DefaultValues() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("0");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertEquals(1, pagination.getPage());
        Assertions.assertEquals(Pagination.DEFAULT_SIZE, pagination.getSize());
    }

    @Test
    @DisplayName("The pagination is paged when receiving a size value of zero")
    public void testResolve_ZeroSize_Paged() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Pagination            pagination;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("0");

        pagination = (Pagination) resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertTrue(pagination.getPaged());
    }

}
