package com.airportcli.client;

import com.airportcli.domain.Airport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

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

    @Test
    public void testBuildAirportListFromValidResponse() {
        RESTClient restClient = new RESTClient(httpClient);

        String json = """
            [
                {
                    "id": 1,
                    "name": "St. John's International Airport",
                    "code": "YYT",
                    "city": {
                        "id": 1,
                        "name": "St. John's",
                        "province": "Newfoundland and Labrador",
                        "population": 110525
                    }
                },
                {
                    "id": 2,
                    "name": "Halifax Stanfield International Airport",
                    "code": "YHZ",
                    "city": {
                        "id": 2,
                        "name": "Halifax",
                        "province": "Nova Scotia",
                        "population": 348634
                    }
                }
            ]
            """;

        List<Airport> airports = restClient.buildAirportListFromResponse(json);

        Assertions.assertEquals(2, airports.size());
        Assertions.assertEquals("YYT", airports.get(0).getCode());
        Assertions.assertEquals("St. John's", airports.get(0).getCity().getName());
        Assertions.assertEquals("YHZ", airports.get(1).getCode());
        Assertions.assertEquals("Halifax", airports.get(1).getCity().getName());
    }

    @Test
    public void testBuildAirportListFromEmptyResponse() {
        RESTClient restClient = new RESTClient(httpClient);

        List<Airport> airports = restClient.buildAirportListFromResponse("[]");

        Assertions.assertTrue(airports.isEmpty());
    }

    @Test
    public void testBuildAirportListFromInvalidResponse() {
        RESTClient restClient = new RESTClient(httpClient);

        List<Airport> airports = restClient.buildAirportListFromResponse("Invalid JSON!");

        Assertions.assertTrue(airports.isEmpty());
    }
}
