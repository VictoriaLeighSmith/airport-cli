package com.airportcli.reports;

import com.airportcli.domain.Airport;
import com.airportcli.domain.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportReports {

    public String airportsByCity(List<Airport> airports) {
        if (airports.isEmpty()) {
            return "No airports found.";
        }

        Map<String, List<Airport>> airportsByCity = new HashMap<>();

        for (Airport airport : airports) {
            City city = airport.getCity();

            if (!airportsByCity.containsKey(city.getName())) {
                ArrayList<Airport> cityAirports = new ArrayList<>();
                cityAirports.add(airport);
                airportsByCity.put(city.getName(), cityAirports);
            } else {
                airportsByCity.get(city.getName()).add(airport);
            }
        }

        StringBuilder report =  new StringBuilder();
        String line = "-".repeat(40);

        report.append("\n");
        report.append("AIRPORTS BY CITY\n");
        report.append(line);
        report.append("\n");

        for (String cityName : airportsByCity.keySet()) {
            List<Airport> cityAirports = airportsByCity.get(cityName);

            report.append(cityName).append(":\n");

            for (Airport airport : cityAirports) {
                report.append("- ").append(airport.getName()).append("\n");
            }

            report.append("\n");
        }

        return report.toString();
    }
}
