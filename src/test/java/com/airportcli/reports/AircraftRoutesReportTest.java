package com.airportcli.reports;

import com.airportcli.domain.Aircraft;
import com.airportcli.domain.Airport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AircraftRoutesReportTest {

    @Test
    public void testEmptyAircraftList() {
        List<Aircraft> aircraft = new ArrayList<>();

        AircraftRoutesReport report = new AircraftRoutesReport();

        String result = report.aircraftRoutes(aircraft);

        Assertions.assertEquals("No aircraft found.", result);
    }

    @Test
    public void testAircraftWithNoAirports() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");
        aircraft.setAirlineName("Air Canada");
        aircraft.setAirports(new ArrayList<>());

        List<Aircraft> aircraftList = new ArrayList<>();
        aircraftList.add(aircraft);

        AircraftRoutesReport report = new AircraftRoutesReport();
        String result = report.aircraftRoutes(aircraftList);

        Assertions.assertTrue(result.contains("Boeing 737"));
        Assertions.assertTrue(result.contains("Air Canada"));
        Assertions.assertTrue(result.contains("No airports found."));
    }

    @Test
    public void testAircraftWithMultipleAirports() {
        Airport airport1 = new Airport();
        airport1.setName("St. John's International");

        Airport airport2 = new Airport();
        airport2.setName("Halifax Stanfield");

        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");
        aircraft.setAirlineName("Air Canada");
        aircraft.setAirports(List.of(airport1, airport2));

        List<Aircraft> aircraftList = List.of(aircraft);

        AircraftRoutesReport report = new AircraftRoutesReport();
        String result = report.aircraftRoutes(aircraftList);

        Assertions.assertTrue(result.contains("Boeing 737"));
        Assertions.assertTrue(result.contains("Air Canada"));
        Assertions.assertTrue(result.contains("St. John's International"));
        Assertions.assertTrue(result.contains("Halifax Stanfield"));
    }

    @Test
    public void testMultipleAircraft() {
        Airport airport1 = new Airport();
        airport1.setName("St. John's International");

        Airport airport2 = new Airport();
        airport2.setName("Toronto Pearson");

        Aircraft aircraft1 = new Aircraft();
        aircraft1.setType("Boeing 737");
        aircraft1.setAirlineName("Air Canada");
        aircraft1.setAirports(List.of(airport1));

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setType("Airbus A320");
        aircraft2.setAirlineName("WestJet");
        aircraft2.setAirports(List.of(airport2));

        List<Aircraft> aircraftList = List.of(aircraft1, aircraft2);

        AircraftRoutesReport report = new AircraftRoutesReport();
        String result = report.aircraftRoutes(aircraftList);

        Assertions.assertTrue(result.contains("Boeing 737 (Air Canada):\n- St. John's International"));
        Assertions.assertTrue(result.contains("Airbus A320 (WestJet):\n- Toronto Pearson"));
    }

    @Test
    public void testAircraftWithNullAirports() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");
        aircraft.setAirlineName("Air Canada");
        aircraft.setAirports(null);

        List<Aircraft> aircraftList = List.of(aircraft);

        AircraftRoutesReport report = new AircraftRoutesReport();
        String result = report.aircraftRoutes(aircraftList);

        Assertions.assertTrue(result.contains("Boeing 737"));
        Assertions.assertTrue(result.contains("No airports found."));
    }
}