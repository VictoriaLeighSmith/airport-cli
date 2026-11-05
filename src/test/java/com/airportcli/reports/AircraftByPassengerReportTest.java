package com.airportcli.reports;

import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AircraftByPassengerReportTest {

    @Test
    public void testEmptyPassengerList() {
        List<Passenger> passengers = new ArrayList<>();

        AircraftByPassengerReport report = new AircraftByPassengerReport();

        String result = report.aircraftByPassenger(passengers);

        Assertions.assertEquals("No passengers found.", result);
    }

    @Test
    public void testPassengerWithNoAircraft() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setAircraft(new ArrayList<>());

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        AircraftByPassengerReport report = new AircraftByPassengerReport();
        String result = report.aircraftByPassenger(passengers);

        Assertions.assertTrue(result.contains("John Smith:"));
        Assertions.assertTrue(result.contains("No aircraft found."));
    }

    @Test
    public void testPassengerWithAircraft() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");
        aircraft.setAirlineName("Air Canada");

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setAircraft(List.of(aircraft));

        List<Passenger> passengers = List.of(passenger);

        AircraftByPassengerReport report = new AircraftByPassengerReport();
        String result = report.aircraftByPassenger(passengers);

        Assertions.assertTrue(result.contains("John Smith:"));
        Assertions.assertTrue(result.contains("Boeing 737"));
        Assertions.assertTrue(result.contains("Air Canada"));
    }

    @Test
    public void testPassengerWithMultipleAircraft() {
        Aircraft aircraft1 = new Aircraft();
        aircraft1.setType("Boeing 737");
        aircraft1.setAirlineName("Air Canada");

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setType("Airbus A320");
        aircraft2.setAirlineName("WestJet");

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setAircraft(List.of(aircraft1, aircraft2));

        List<Passenger> passengers = List.of(passenger);

        AircraftByPassengerReport report = new AircraftByPassengerReport();
        String result = report.aircraftByPassenger(passengers);

        Assertions.assertTrue(result.contains("John Smith:"));
        Assertions.assertTrue(result.contains("Boeing 737"));
        Assertions.assertTrue(result.contains("Air Canada"));
        Assertions.assertTrue(result.contains("Airbus A320"));
        Assertions.assertTrue(result.contains("WestJet"));
    }

    @Test
    public void testMultiplePassengers() {
        Aircraft aircraft1 = new Aircraft();
        aircraft1.setType("Boeing 737");
        aircraft1.setAirlineName("Air Canada");

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setType("Airbus A320");
        aircraft2.setAirlineName("WestJet");

        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("John");
        passenger1.setLastName("Smith");
        passenger1.setAircraft(List.of(aircraft1));

        Passenger passenger2 = new Passenger();
        passenger2.setFirstName("Jane");
        passenger2.setLastName("Doe");
        passenger2.setAircraft(List.of(aircraft2));

        List<Passenger> passengers = List.of(passenger1, passenger2);

        AircraftByPassengerReport report = new AircraftByPassengerReport();
        String result = report.aircraftByPassenger(passengers);

        Assertions.assertTrue(result.contains("John Smith:\n- Boeing 737 (Air Canada)"));
        Assertions.assertTrue(result.contains("Jane Doe:\n- Airbus A320 (WestJet)"));
    }
}