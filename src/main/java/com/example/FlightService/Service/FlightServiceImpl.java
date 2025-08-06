package com.example.FlightService.Service;

import com.example.FlightService.Entity.Flight;
import com.example.FlightService.Exception.FlightServiceException;
import com.example.FlightService.Model.SearchFlights;
import com.example.FlightService.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public List<String> getSources() {
        List<String> sources = flightRepository.findDistinctSources();
        if (sources == null || sources.isEmpty()) {
            throw new FlightServiceException("No source data available");
        }
        return sources;
    }

    @Override
    public List<String> getDestinations() {
        List<String> destinations = flightRepository.findDistinctDestinations();
        if (destinations == null || destinations.isEmpty()) {
            throw new FlightServiceException("No destination data available");
        }
        return destinations;
    }

    @Override
    public List<SearchFlights> searchFlights(String source, String destination, Date journeyDate) {
        List<Flight> flights = flightRepository.findFlightsByRouteAndDate(source, destination, journeyDate);
        List<SearchFlights> response = new ArrayList<>();
        for (Flight flight : flights) {
            SearchFlights dto = new SearchFlights();
            dto.setFlightId(flight.getFlightId());
            dto.setSource(flight.getSource());
            dto.setDestination(flight.getDestination());
            dto.setJourneyDate(flight.getFlightAvailableDate());
            dto.setDepartureTime(flight.getDepartureTime());
            dto.setArrivalTime(flight.getArrivalTime());
            dto.setSeatCount(flight.getSeatCount().toString());
            dto.setAirlines(flight.getAirlines());
            dto.setFare(Double.toString(flight.getFare()));
            response.add(dto);
        }
        return response;
    }

    @Override
    public void updateSeatCount(String flightId, int seatsToBook) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightServiceException("Flight not found: " + flightId));
        int updatedCount = flight.getSeatCount() - seatsToBook;
        if (updatedCount < 0) {
            throw new FlightServiceException("Not enough seats available");
        }
        flight.setSeatCount(updatedCount);
        flightRepository.save(flight);
    }

    @Override
    public Flight getFlightById(String flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightServiceException("Flight not found: " + flightId));
    }
}
