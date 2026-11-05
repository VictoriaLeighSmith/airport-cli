package com.airportcli.reports;

import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Airport;

import java.util.List;

public class AircraftRoutesReport {
    public String aircraftRoutes(List<Aircraft> aircraft) {
        if (aircraft.isEmpty()) {
            return "No aircraft found.";
        }

        StringBuilder report = new StringBuilder();

        report.append("\n");
        report.append("AIRCRAFT ROUTES\n");
        report.append("-".repeat(40));
        report.append("\n");

        for (Aircraft plane : aircraft) {
            report.append(plane.getType() + " (" + plane.getAirlineName() + "):\n");
            if (plane.getAirports() == null || plane.getAirports().isEmpty()) {
                report.append("- No airports found.\n");
            } else {
                for (Airport airport : plane.getAirports()) {
                    report.append("- " + airport.getName() + "\n");
                }
            }

            report.append("\n");
        }

        return report.toString();
    }
}
