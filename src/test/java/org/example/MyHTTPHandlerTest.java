package org.example;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


class MyHTTPHandlerTest {


    @Test
    void testParseFormData() throws IOException {

        HttpExchange exchange = mock(HttpExchange.class);

        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream("key1=value1&key2=value2".getBytes()));

        MyHTTPHandler handler = new MyHTTPHandler();

        var formData = handler.parseFormData(exchange);

        assertEquals("value1", formData.get("key1"));
        assertEquals("value2", formData.get("key2"));
    }
}
