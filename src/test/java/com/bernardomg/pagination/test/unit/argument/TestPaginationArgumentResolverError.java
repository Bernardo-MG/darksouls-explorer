
package com.bernardomg.pagination.test.unit.argument;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.pagination.argument.PaginationArgumentResolver;

@DisplayName("Pagination argument resolver error handling")
public class TestPaginationArgumentResolverError {

    private final HandlerMethodArgumentResolver resolver = new PaginationArgumentResolver();

    public TestPaginationArgumentResolverError() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when receiving a text for page")
    public void testResolve_StringPage_Exception() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Executable            executable;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("abc");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("10");

        executable = () -> resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertThrows(NumberFormatException.class, executable);
    }

    @Test
    @DisplayName("Throws an exception when receiving a text for size")
    public void testResolve_StringSize_Exception() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Executable            executable;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("abc");

        executable = () -> resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertThrows(NumberFormatException.class, executable);
    }

}
