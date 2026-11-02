package com.airportcli.reports;

import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Airport;
import com.airportcli.domain.City;
import com.airportcli.domain.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AirportsUsedByPassengersReportTest {

    @Test
    public void testEmptyPassengerList() {
        List<Passenger> passengers = new ArrayList<>();

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();
        String report = airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);

        String expected = "No passengers found.";

        Assertions.assertEquals(expected, report);
    }

    @Test
    public void testPassengerWithMultipleAirports() {
        List<Airport> airports = new ArrayList<>();

        Airport airport1 = new Airport();
        airport1.setName("St. John's International");

        Airport airport2 = new Airport();
        airport2.setName("Halifax Stanfield");

        airports.add(airport1);
        airports.add(airport2);

        Aircraft aircraft = new Aircraft();
        aircraft.setAirports(airports);

        List<Aircraft> aircraftList = new ArrayList<>();
        aircraftList.add(aircraft);

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setAircraft(aircraftList);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        String expected = """
        
        AIRPORTS USED BY PASSENGERS
        ----------------------------------------
        John Smith:
        - St. John's International
        - Halifax Stanfield
        
        """;

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();
        String report = airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);

        Assertions.assertEquals(expected, report);
    }

    @Test
    public void testMultiplePassengers() {
        List<Airport> airports = new ArrayList<>();

        Airport airport = new Airport();
        airport.setName("St. John's International");

        airports.add(airport);

        Aircraft aircraft = new Aircraft();
        aircraft.setAirports(airports);

        List<Aircraft> aircraftList = new ArrayList<>();
        aircraftList.add(aircraft);

        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("John");
        passenger1.setLastName("Smith");
        passenger1.setAircraft(aircraftList);

        Passenger passenger2 = new Passenger();
        passenger2.setFirstName("Jane");
        passenger2.setLastName("Smith");
        passenger2.setAircraft(aircraftList);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger1);
        passengers.add(passenger2);

        String expected = """
        
        AIRPORTS USED BY PASSENGERS
        ----------------------------------------
        John Smith:
        - St. John's International
        
        Jane Smith:
        - St. John's International
        
        """;

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();
        String report = airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);

        Assertions.assertEquals(expected, report);
    }

    @Test
    public void testDuplicateAirports() {
        Airport airport1 = new Airport();
        airport1.setName("St. John's International");

        Airport airport2 = new Airport();
        airport2.setName("Halifax Stanfield");

        Airport airport3 = new Airport();
        airport3.setName("Toronto Pearson");

        Aircraft aircraft1 = new Aircraft();
        List<Airport> airportList1 = new ArrayList<>();

        airportList1.add(airport1);
        airportList1.add(airport2);

        aircraft1.setAirports(airportList1);

        Aircraft aircraft2 = new Aircraft();
        List<Airport> airportList2 = new ArrayList<>();

        airportList2.add(airport2);
        airportList2.add(airport3);

        aircraft2.setAirports(airportList2);

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");

        List<Aircraft> aircraftList = new ArrayList<>();
        aircraftList.add(aircraft1);
        aircraftList.add(aircraft2);

        passenger.setAircraft(aircraftList);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        String expected = """
        
        AIRPORTS USED BY PASSENGERS
        ----------------------------------------
        John Smith:
        - St. John's International
        - Halifax Stanfield
        - Toronto Pearson
        
        """;

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();
        String report = airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);

        Assertions.assertEquals(expected, report);
    }

    @Test
    public void testPassengerWithNoAirports() {
        List<Airport> airports = new ArrayList<>();

        Aircraft aircraft = new Aircraft();
        aircraft.setAirports(airports);

        List<Aircraft> aircraftList = new ArrayList<>();
        aircraftList.add(aircraft);

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setAircraft(aircraftList);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        String expected = """
        
        AIRPORTS USED BY PASSENGERS
        ----------------------------------------
        John Smith:
        - No airports found.
        
        """;

        AirportsUsedByPassengersReport airportsUsedByPassengersReport = new AirportsUsedByPassengersReport();
        String report = airportsUsedByPassengersReport.airportsUsedByPassengers(passengers);

        Assertions.assertEquals(expected, report);
    }
}
