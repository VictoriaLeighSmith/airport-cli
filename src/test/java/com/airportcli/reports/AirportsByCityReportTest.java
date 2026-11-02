package com.airportcli.reports;

import com.airportcli.domain.Airport;
import com.airportcli.domain.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AirportsByCityReportTest {

    @Test
    public void testMultipleAirportsInSameCity() {
        City city = new City(1L, "Toronto", "Ontario", 1000000);
        Airport torontoAirport1 = new Airport(1L, "Toronto Pearson International Airport", "YYZ", city);
        Airport torontoAirport2 = new Airport(2L, "Billy Bishop Toronto City Airport", "YTZ", city);

        List<Airport> airports = new ArrayList<>();

        airports.add(torontoAirport1);
        airports.add(torontoAirport2);

        AirportsByCityReport airportsByCityReport = new AirportsByCityReport();

        String report = airportsByCityReport.airportsByCity(airports);

        String expected = """
        
        AIRPORTS BY CITY
        ----------------------------------------
        Toronto:
        - Toronto Pearson International Airport
        - Billy Bishop Toronto City Airport
        
        """;

        Assertions.assertEquals(expected, report);
    }

    @Test
    public void testAirportsGroupedByCity() {
        City toronto = new City(1L, "Toronto", "Ontario", 1000000);
        City halifax = new City(2L, "Halifax", "Nova Scotia", 1000000);

        Airport torontoAirport1 = new Airport(1L, "Toronto Pearson International Airport", "YYZ", toronto);
        Airport torontoAirport2 = new Airport(2L, "Billy Bishop Toronto City Airport", "YTZ", toronto);
        Airport halifaxAirport = new Airport(3L, "Halifax Stanfield Airport", "YHZ", halifax);

        List<Airport> airports = new ArrayList<>();

        airports.add(torontoAirport1);
        airports.add(torontoAirport2);
        airports.add(halifaxAirport);

        AirportsByCityReport airportsByCityReport = new AirportsByCityReport();

        String report = airportsByCityReport.airportsByCity(airports);

        Assertions.assertTrue(report.contains("Toronto:\n- Toronto Pearson International Airport\n- Billy Bishop Toronto City Airport"));
        Assertions.assertTrue(report.contains("Halifax:\n- Halifax Stanfield Airport"));
    }

    @Test
    public void testAirportsByCityWithEmptyList() {
        List<Airport> airports = new ArrayList<>();
        AirportsByCityReport airportsByCityReport = new AirportsByCityReport();

        String report = airportsByCityReport.airportsByCity(airports);

        String expected = "No airports found.";

        Assertions.assertEquals(expected, report);
    }
}
