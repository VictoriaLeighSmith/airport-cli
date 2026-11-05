package com.airportcli.cli;

import com.airportcli.client.RESTClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AirportMenuTest {

    @Mock
    private RESTClient restClient;

    AirportMenu airportMenu = new AirportMenu();

    @Test
    public void testSuccessfulAirportsByCity() {
        Mockito.when(restClient.getResponseFromHTTPRequest("http://localhost:8080/api/airports")).thenReturn("[]");
        Mockito.when(restClient.buildAirportListFromResponse("[]")).thenReturn(List.of());

        String result = airportMenu.displayAirportsByCity(restClient);

        Assertions.assertEquals("No airports found.", result);
    }

    @Test
    public void testSuccessfulAircraftByPassenger() {
        Mockito.when(restClient.getResponseFromHTTPRequest("http://localhost:8080/api/passengers")).thenReturn("[]");
        Mockito.when(restClient.buildPassengerListFromResponse("[]")).thenReturn(List.of());

        String result = airportMenu.displayAircraftByPassenger(restClient);

        Assertions.assertEquals("No passengers found.", result);
    }

    @Test
    public void testSuccessfulAircraftRoutes() {
        Mockito.when(restClient.getResponseFromHTTPRequest("http://localhost:8080/api/aircraft")).thenReturn("[]");
        Mockito.when(restClient.buildAircraftListFromResponse("[]")).thenReturn(List.of());

        String result = airportMenu.displayAircraftRoutes(restClient);

        Assertions.assertEquals("No aircraft found.", result);
    }

    @Test
    public void testSuccessfulAirportsByPassengers() {
        Mockito.when(restClient.getResponseFromHTTPRequest("http://localhost:8080/api/passengers")).thenReturn("[]");
        Mockito.when(restClient.buildPassengerListFromResponse("[]")).thenReturn(List.of());

        String result = airportMenu.displayAirportsByPassengers(restClient);

        Assertions.assertEquals("No passengers found.", result);
    }

    @Test
    public void testFailedHTTPRequest() {
        Mockito.when(restClient.getResponseFromHTTPRequest("http://localhost:8080/api/airports")).thenReturn(null);

        String result = airportMenu.displayAirportsByCity(restClient);

        Assertions.assertEquals("[ERROR] Airport data could not be retrieved.", result);
    }

    @Test
    public void testFailedJSONDeserialization() {
        Mockito.when(restClient.getResponseFromHTTPRequest("http://localhost:8080/api/airports")).thenReturn("Invalid JSON!");
        Mockito.when(restClient.buildAirportListFromResponse("Invalid JSON!")).thenReturn(null);

        String result = airportMenu.displayAirportsByCity(restClient);

        Assertions.assertEquals("[ERROR] Airport data could not be processed.", result);
    }
}
