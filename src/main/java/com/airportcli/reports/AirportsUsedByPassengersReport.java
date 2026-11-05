package com.airportcli.reports;

import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Airport;
import com.airportcli.domain.Passenger;

import java.util.ArrayList;
import java.util.List;

public class AirportsUsedByPassengersReport {

    public String airportsUsedByPassengers(List<Passenger> passengers) {
        if (passengers.isEmpty()) {
            return "No passengers found.";
        }

        StringBuilder report =  new StringBuilder();
        String line = "-".repeat(40);

        report.append("\n");
        report.append("AIRPORTS USED BY PASSENGERS\n");
        report.append(line);
        report.append("\n");

        for (Passenger passenger : passengers) {
            List<String> airports = new ArrayList<>();

            if (passenger.getAircraft() != null) {
                for (Aircraft aircraft : passenger.getAircraft()) {
                    if (aircraft.getAirports() != null) {
                        for (Airport airport : aircraft.getAirports()) {
                            if (!airports.contains(airport.getName())) {
                                airports.add(airport.getName());
                            }
                        }
                    }
                }
            }

            report.append(passenger.getFirstName() + " " + passenger.getLastName() + ":\n");

            if (airports.isEmpty()) {
                report.append("- No airports found.\n");
            } else {
                for (String airport : airports) {
                    report.append("- " + airport + "\n");
                }
            }

            report.append("\n");
        }

        return report.toString();
    }
}
