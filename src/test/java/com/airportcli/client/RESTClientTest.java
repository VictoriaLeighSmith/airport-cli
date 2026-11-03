package com.airportcli.client;

import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Airport;
import com.airportcli.domain.Passenger;
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

    @Test
    public void testBuildPassengerListFromValidResponse() {
        RESTClient restClient = new RESTClient(httpClient);

        String json = """
                [
                    {
                        "id": 1,
                        "firstName": "John",
                        "lastName": "Smith",
                        "phoneNumber": "709-555-1234",
                        "city": {
                            "id": 1,
                            "name": "St. John's",
                            "province": "Newfoundland and Labrador",
                            "population": 110525
                        },
                        "aircraft": [
                            {
                                "id": 1,
                                "type": "Boeing 737",
                                "airlineName": "Air Canada",
                                "numberOfPassengers": 150,
                                "airports": [
                                    {
                                        "id": 1,
                                        "name": "St. John's International Airport",
                                        "code": "YYT"
                                    }
                                ]
                            }
                        ]
                    }
                ]
                """;

        List<Passenger> passengers = restClient.buildPassengerListFromResponse(json);

        Assertions.assertEquals(1, passengers.size());
        Assertions.assertEquals("John", passengers.get(0).getFirstName());
        Assertions.assertEquals("Smith", passengers.get(0).getLastName());
        Assertions.assertEquals("709-555-1234", passengers.get(0).getPhoneNumber());
        Assertions.assertEquals("St. John's", passengers.get(0).getCity().getName());
        Assertions.assertEquals("Boeing 737", passengers.get(0).getAircraft().get(0).getType());
        Assertions.assertEquals(1, passengers.get(0).getAircraft().get(0).getAirports().size());
        Assertions.assertEquals("YYT", passengers.get(0).getAircraft().get(0).getAirports().get(0).getCode());
    }

    @Test
    public void testBuildAircraftListFromValidResponse() {
        RESTClient restClient = new RESTClient(httpClient);

        String json = """
        [
            {
                "id": 1,
                "type": "Boeing 737",
                "airlineName": "Air Canada",
                "numberOfPassengers": 180,
                "passengers": [],
                "airports": [
                    {
                        "id": 2,
                        "name": "St. John's International Airport",
                        "code": "YYT"
                    }
                ]
            }
        ]
        """;

        List<Aircraft> aircraft =
                restClient.buildAircraftListFromResponse(json);

        Assertions.assertEquals(1, aircraft.size());
        Assertions.assertEquals("Boeing 737", aircraft.get(0).getType());
        Assertions.assertEquals("Air Canada", aircraft.get(0).getAirlineName());
        Assertions.assertEquals(1, aircraft.get(0).getAirports().size());
        Assertions.assertEquals("YYT",
                aircraft.get(0).getAirports().get(0).getCode());
    }
}
