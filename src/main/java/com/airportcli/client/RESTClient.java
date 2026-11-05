package com.airportcli.client;

import com.airportcli.domain.Airport;
import com.airportcli.domain.Passenger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.airportcli.domain.Aircraft;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RESTClient(HttpClient httpClient) {
        this.httpClient = httpClient;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String getResponseFromHTTPRequest(String requestUrl) {
        String responseBody = null;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                responseBody = response.body();
            }
        } catch (IOException e) {
            System.err.println("[ERROR] HTTP request failed: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[ERROR] HTTP request interrupted: " + e.getMessage());
        }

        return responseBody;
    }

    public List<Airport> buildAirportListFromResponse(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<List<Airport>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<Passenger> buildPassengerListFromResponse(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<List<Passenger>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<Aircraft> buildAircraftListFromResponse(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<List<Aircraft>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
