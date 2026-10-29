package com.airportcli.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@ExtendWith(MockitoExtension.class)
public class RESTClientTest {
    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @Test
    public void testSuccessfulGetRequest() throws InterruptedException, IOException {
        RESTClient restClient = new RESTClient(httpClient);

        Mockito.when(httpResponse.statusCode()).thenReturn(200);
        Mockito.when(httpResponse.body()).thenReturn("Test Response");
        Mockito.when(httpClient.send(
                Mockito.any(),
                Mockito.any(HttpResponse.BodyHandler.class)
        )).thenReturn(httpResponse);

        String response = restClient.getResponseFromHTTPRequest("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals("Test Response", response);
    }

    @Test
    public void testUnsuccessfulGetRequest() throws InterruptedException, IOException {
        RESTClient restClient = new RESTClient(httpClient);

        Mockito.when(httpResponse.statusCode()).thenReturn(404);
        Mockito.when(httpClient.send(
                Mockito.any(),
                Mockito.any(HttpResponse.BodyHandler.class)
        )).thenReturn(httpResponse);

        String response = restClient.getResponseFromHTTPRequest("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals("", response);
    }
}
