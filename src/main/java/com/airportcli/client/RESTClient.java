package com.airportcli.client;

import com.airportcli.domain.Airport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {
    private final HttpClient httpClient;

    public RESTClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getResponseFromHTTPRequest(String requestUrl) {
        String responseBody = "";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // We could be more elaborate here and handle status codes differently/only assign the response body in success cases?
            if (response.statusCode() == 200) {
                responseBody = response.body();
            } else {
                System.out.println("Status Code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            // Printing stack trace for now like his example, we may want to change this to print out some user facing messaging.
            e.printStackTrace();
        }

        return responseBody;
    }

    public List<Airport> buildAirportListFromResponse(String response) {
        List<Airport> airports = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            airports = objectMapper.readValue(response, new TypeReference<List<Airport>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return airports;
    }
}
