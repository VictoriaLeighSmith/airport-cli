package com.airportcli.reports;
import com.airportcli.domain.Aircraft;
import java.util.List;
import com.airportcli.domain.Airport;

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
        for (Aircraft a : aircraft) {
            report.append("- " + a.getType() + " (" + a.getAirlineName() + ")\n");
            if (a.getAirports() == null || a.getAirports().isEmpty()) {
                report.append("  - No airports found.\n");
            }else {
                for (Airport airport : a.getAirports()) {
                    report.append("  - " + airport.getName() + "\n");
                }
            }
        }
        return report.toString();
    }
}
