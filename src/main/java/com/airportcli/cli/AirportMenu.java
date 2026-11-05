package com.airportcli.cli;

import com.airportcli.client.RESTClient;
import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Airport;
import com.airportcli.domain.Passenger;
import com.airportcli.reports.AirportsByCityReport;
import com.airportcli.reports.AirportsUsedByPassengersReport;
import com.airportcli.reports.AircraftByPassengerReport;
import com.airportcli.reports.AircraftRoutesReport;

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
                    System.out.println(displayAircraftByPassenger(restClient));
                    break;
                case 3:
                    System.out.println(displayAircraftRoutes(restClient));
                    break;
                case 4:
                    System.out.println(displayAirportsByPassengers(restClient));
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Exiting...");
                    running = false;
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

        if (response == null) {
            return "[ERROR] Airport data could not be retrieved.";
        }

        List<Airport> airports = restClient.buildAirportListFromResponse(response);

        if (airports == null) {
            return "[ERROR] Airport data could not be processed.";
        }

        AirportsByCityReport airportsByCityReport = new AirportsByCityReport();

        return airportsByCityReport.airportsByCity(airports);
    }

    public String displayAirportsByPassengers(RESTClient restClient) {
        String response = restClient.getResponseFromHTTPRequest("http://localhost:8080/api/passengers");

        if (response == null) {
            return "[ERROR] Passenger data could not be retrieved.";
        }

        List<Passenger> passengers = restClient.buildPassengerListFromResponse(response);

        if (passengers == null) {
            return "[ERROR] Passenger data could not be processed.";
        }

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();

        return airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);
    }

    public String displayAircraftByPassenger(RESTClient restClient) {
        String response = restClient.getResponseFromHTTPRequest("http://localhost:8080/api/passengers");

        if (response == null) {
            return "[ERROR] Passenger data could not be retrieved.";
        }

        List<Passenger> passengers = restClient.buildPassengerListFromResponse(response);

        if (passengers == null) {
            return "[ERROR] Passenger data could not be processed.";
        }

        AircraftByPassengerReport aircraftByPassengerReport = new AircraftByPassengerReport();

        return aircraftByPassengerReport.aircraftByPassenger(passengers);
    }

    public String displayAircraftRoutes(RESTClient restClient) {
        String response = restClient.getResponseFromHTTPRequest("http://localhost:8080/api/aircraft");

        if (response == null) {
            return "[ERROR] Aircraft data could not be retrieved.";
        }

        List<Aircraft> aircraft = restClient.buildAircraftListFromResponse(response);

        if (aircraft == null) {
            return "[ERROR] Aircraft data could not be processed.";
        }

        AircraftRoutesReport aircraftRoutesReport = new AircraftRoutesReport();

        return aircraftRoutesReport.aircraftRoutes(aircraft);
    }
}
