
package com.bernardomg.darksouls.explorer.test.unit.response.controller;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.bernardomg.pagination.model.PageIterable;
import com.bernardomg.response.controller.ResponseAdvice;
import com.bernardomg.response.model.DefaultPaginatedResponse;
import com.bernardomg.response.model.DefaultResponse;
import com.bernardomg.response.model.PaginatedResponse;
import com.bernardomg.response.model.Response;

@DisplayName("Response Advice")
public class TestResponseAdvice {

    private final ResponseBodyAdvice<Object> advice = new ResponseAdvice();

    public TestResponseAdvice() {
        super();
    }

    @Test
    @DisplayName("A null is wrapped")
    public void testResponse_Null_WrappedType() {
        final Object body;
        final Object response;

        body = null;

        response = handleResponse(body);

        Assertions.assertInstanceOf(Response.class, response);
    }

    @Test
    @DisplayName("A page content is returned back")
    public void testResponse_Page_WrappedContent() {
        final Page<?>              body;
        final PaginatedResponse<?> response;

        body = Mockito.mock(Page.class);
        Mockito.when(body.getContent())
            .thenReturn(Collections.emptyList());
        Mockito.when(body.getNumberOfElements())
            .thenReturn(1);
        Mockito.when(body.getNumber())
            .thenReturn(2);
        Mockito.when(body.getSize())
            .thenReturn(3);
        Mockito.when(body.getTotalElements())
            .thenReturn(4l);
        Mockito.when(body.getTotalPages())
            .thenReturn(5);
        Mockito.when(body.isFirst())
            .thenReturn(true);
        Mockito.when(body.isLast())
            .thenReturn(false);

        response = (PaginatedResponse<?>) handleResponse(body);

        Assertions.assertEquals(response.getContent(), body.getContent());
        Assertions.assertEquals(response.getElementsInPage(), body.getNumberOfElements());
        Assertions.assertEquals(response.getPageNumber(), body.getNumber());
        Assertions.assertEquals(response.getSize(), body.getSize());
        Assertions.assertEquals(response.getTotalElements(), body.getTotalElements());
        Assertions.assertEquals(response.getTotalPages(), body.getTotalPages());
        Assertions.assertEquals(response.getFirst(), body.isFirst());
        Assertions.assertEquals(response.getLast(), body.isLast());
    }

    @Test
    @DisplayName("A page is wrapped")
    public void testResponse_Page_WrappedType() {
        final Object body;
        final Object response;

        body = Mockito.mock(Page.class);

        response = handleResponse(body);

        Assertions.assertInstanceOf(PaginatedResponse.class, response);
    }

    @Test
    @DisplayName("A page iterable content is returned back")
    public void testResponse_PageIterable_WrappedContent() {
        final PageIterable<?>      body;
        final PaginatedResponse<?> response;

        body = Mockito.mock(PageIterable.class);
        Mockito.when(body.getContent())
            .thenReturn(Collections.emptyList());
        Mockito.when(body.getElementsInPage())
            .thenReturn(1);
        Mockito.when(body.getPageNumber())
            .thenReturn(2);
        Mockito.when(body.getSize())
            .thenReturn(3);
        Mockito.when(body.getTotalElements())
            .thenReturn(4l);
        Mockito.when(body.getTotalPages())
            .thenReturn(5);
        Mockito.when(body.isFirst())
            .thenReturn(true);
        Mockito.when(body.isLast())
            .thenReturn(false);

        response = (PaginatedResponse<?>) handleResponse(body);

        Assertions.assertEquals(response.getContent(), body.getContent());
        Assertions.assertEquals(response.getElementsInPage(), body.getElementsInPage());
        Assertions.assertEquals(response.getPageNumber(), body.getPageNumber());
        Assertions.assertEquals(response.getSize(), body.getSize());
        Assertions.assertEquals(response.getTotalElements(), body.getTotalElements());
        Assertions.assertEquals(response.getTotalPages(), body.getTotalPages());
        Assertions.assertEquals(response.getFirst(), body.isFirst());
        Assertions.assertEquals(response.getLast(), body.isLast());
    }

    @Test
    @DisplayName("A page iterable is wrapped")
    public void testResponse_PageIterable_WrappedType() {
        final Object body;
        final Object response;

        body = Mockito.mock(PageIterable.class);

        response = handleResponse(body);

        Assertions.assertInstanceOf(PaginatedResponse.class, response);
    }

    @Test
    @DisplayName("A paginated response content is returned back")
    public void testResponse_PaginatedResponse_ReturnsContentBack() {
        final Object body;
        final Object response;

        body = new DefaultPaginatedResponse<>();

        response = handleResponse(body);

        Assertions.assertEquals(response, body);
    }

    @Test
    @DisplayName("A paginated response is returned back")
    public void testResponse_PaginatedResponse_ReturnsTypeBack() {
        final Object body;
        final Object response;

        body = new DefaultPaginatedResponse<>();

        response = handleResponse(body);

        Assertions.assertInstanceOf(PaginatedResponse.class, response);
    }

    @Test
    @DisplayName("A response content is returned back")
    public void testResponse_Response_ReturnsContentBack() {
        final Object body;
        final Object response;

        body = new DefaultResponse<>();

        response = handleResponse(body);

        Assertions.assertEquals(response, body);
    }

    @Test
    @DisplayName("A response is returned back")
    public void testResponse_Response_ReturnsTypeBack() {
        final Object body;
        final Object response;

        body = new DefaultResponse<>();

        response = handleResponse(body);

        Assertions.assertInstanceOf(Response.class, response);
    }

    @Test
    @DisplayName("A Spring response content is returned back")
    public void testResponse_ResponseEntity_ReturnsContentBack() {
        final Object body;
        final Object response;

        body = new ResponseEntity<>(HttpStatus.OK);

        response = handleResponse(body);

        Assertions.assertEquals(response, body);
    }

    @Test
    @DisplayName("A Spring response is returned back")
    public void testResponse_ResponseEntity_ReturnsTypeBack() {
        final Object body;
        final Object response;

        body = new ResponseEntity<>(HttpStatus.OK);

        response = handleResponse(body);

        Assertions.assertInstanceOf(ResponseEntity.class, response);
    }

    @Test
    @DisplayName("A random body content is returned back")
    public void testResponse_String_WrappedContent() {
        final Object      body;
        final Response<?> response;

        body = "abc";

        response = (Response<?>) handleResponse(body);

        Assertions.assertEquals(response.getContent(), body);
    }

    @Test
    @DisplayName("A random body is wrapped")
    public void testResponse_String_WrappedType() {
        final Object body;
        final Object response;

        body = "abc";

        response = handleResponse(body);

        Assertions.assertInstanceOf(Response.class, response);
    }

    private final Object handleResponse(final Object body) {
        final MethodParameter                          returnType;
        final MediaType                                selectedContentType;
        final Class<? extends HttpMessageConverter<?>> selectedConverterType;
        final ServerHttpRequest                        request;
        final ServerHttpResponse                       response;

        returnType = Mockito.mock(MethodParameter.class);
        selectedContentType = Mockito.mock(MediaType.class);
        selectedConverterType = null;
        request = Mockito.mock(ServerHttpRequest.class);
        response = Mockito.mock(ServerHttpResponse.class);

        return advice.beforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);
    }

}
