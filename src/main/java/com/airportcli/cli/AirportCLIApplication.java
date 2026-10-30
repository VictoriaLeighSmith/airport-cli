package com.airportcli.cli;

import com.airportcli.client.RESTClient;

import java.net.http.HttpClient;
import java.util.Scanner;

public class AirportCLIApplication {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        RESTClient restClient = new RESTClient(httpClient);

        AirportMenu airportMenu = new AirportMenu();
        Scanner scanner = new Scanner(System.in);

        airportMenu.displayMenu(scanner, restClient);
    }
}
