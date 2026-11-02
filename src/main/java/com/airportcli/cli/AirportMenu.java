package com.airportcli.cli;

import com.airportcli.client.RESTClient;
import com.airportcli.domain.Airport;
import com.airportcli.domain.Passenger;
import com.airportcli.reports.AirportsByCityReport;
import com.airportcli.reports.AirportsUsedByPassengersReport;

import java.util.List;
import java.util.Scanner;

public class AirportMenu {

    public void displayMenu(Scanner scanner, RESTClient restClient) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("*".repeat(50));
            System.out.println("*           AIRPORT INFORMATION SYSTEM           *");
            System.out.println("*".repeat(50));
            System.out.println();
            System.out.println("1. View Airports by City");
            System.out.println("2. View Aircraft by Passenger");
            System.out.println("3. View Aircraft Routes");
            System.out.println("4. View Airports Used by Passengers");
            System.out.println("5. Exit");
            System.out.println();
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println();
                System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 5.");
                System.out.println();

                scanner.nextLine();
                continue;
            }

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    System.out.println(displayAirportsByCity(restClient));
                    break;
                case 2:
                    System.out.println("[INFO] Displaying aircraft by passenger ...");
                    break;
                case 3:
                    System.out.println("[INFO] Displaying aircraft routes ...");
                    break;
                case 4:
                    System.out.println(displayAirportsByPassengers(restClient));
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Exiting...");
                    running =  false;
                    break;
                default:
                    System.out.println();
                    System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 5");
                    System.out.println();
            }
        }
    }

    public String displayAirportsByCity(RESTClient restClient) {
        String response = restClient.getResponseFromHTTPRequest("http://localhost:8080/api/airports");
        List<Airport> airports = restClient.buildAirportListFromResponse(response);

        AirportsByCityReport airportsByCityReport = new AirportsByCityReport();

        return airportsByCityReport.airportsByCity(airports);
    }

    public String displayAirportsByPassengers(RESTClient restClient) {
        String response = restClient.getResponseFromHTTPRequest("http://localhost:8080/api/passengers");
        List<Passenger> passengers = restClient.buildPassengerListFromResponse(response);

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();

        return airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);
    }
}
