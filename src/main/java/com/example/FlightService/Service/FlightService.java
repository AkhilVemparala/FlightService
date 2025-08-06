package com.example.FlightService.Service;

import com.example.FlightService.Entity.Flight;
import com.example.FlightService.Model.SearchFlights;

import java.util.Date;
import java.util.List;

public interface FlightService {
    List<String> getSources();
    List<String> getDestinations();
    List<SearchFlights> searchFlights(String source, String destination, Date journeyDate);
    void updateSeatCount(String flightId, int seatsToBook);
    Flight getFlightById(String flightId);
}
