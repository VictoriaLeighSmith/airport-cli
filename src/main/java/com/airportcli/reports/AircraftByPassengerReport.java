package com.airportcli.reports;
import com.airportcli.domain.Passenger;
import com.airportcli.domain.Aircraft;
import java.util.List;

public class AircraftByPassengerReport {
    public String aircraftByPassenger(List<Passenger> passengers) {
        if (passengers.isEmpty()) {
            return "No passengers found.";
        }
        StringBuilder report = new StringBuilder();

        report.append("\n");
        report.append("AIRCRAFT BY PASSENGER\n");
        report.append("-".repeat(40));
        report.append("\n");

        for (Passenger passenger : passengers) {
            report.append(passenger.getFirstName() + " "
                    + passenger.getLastName() + ":\n");
            if (passenger.getAircraft() == null || passenger.getAircraft().isEmpty()) {
                report.append("- No aircraft found.\n");
            }else {
                for (Aircraft aircraft : passenger.getAircraft()) {
                    report.append("- " + aircraft.getType()
                            + " (" + aircraft.getAirlineName() + ")\n");
                }
            }
        }

        return report.toString();
    }
}
