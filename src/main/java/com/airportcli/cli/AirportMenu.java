package com.airportcli.cli;

import com.airportcli.client.RESTClient;

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
                    System.out.println("[INFO] Displaying airports by city ...");
                    break;
                case 2:
                    System.out.println("[INFO] Displaying aircraft by passenger ...");
                    break;
                case 3:
                    System.out.println("[INFO] Displaying aircraft routes ...");
                    break;
                case 4:
                    System.out.println("[INFO] Displaying airports used by passengers ...");
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
}
