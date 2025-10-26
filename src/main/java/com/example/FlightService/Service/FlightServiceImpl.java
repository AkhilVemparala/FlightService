package com.example.FlightService.Service;

import com.example.FlightService.Entity.Flight;
import com.example.FlightService.Exception.FlightServiceException;
import com.example.FlightService.Model.SearchFlights;
import com.example.FlightService.Repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    @Cacheable("sources")
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
    @CachePut(value = "flight", key = "#flightId")
    public Flight updateSeatCount(String flightId, int seatsToBook) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightServiceException("Flight not found: " + flightId));
        int updatedCount = flight.getSeatCount() - seatsToBook;
        if (updatedCount < 0) {
            throw new FlightServiceException("Not enough seats available");
        }
        flight.setSeatCount(updatedCount);
        return flightRepository.save(flight);
    }

    @Override
    @Cacheable(value = "flight", key = "#flightId")
    public Flight getFlightById(String flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightServiceException("Flight not found: " + flightId));
    }

    @Override
    @CacheEvict(value = "flight", key = "#flight.flightId")
    public Boolean addFlight(Flight flight) {
        if (flightRepository.existsById(flight.getFlightId())) {
            throw new FlightServiceException("Flight with ID " + flight.getFlightId() + " already exists");
        }
        flightRepository.save(flight);
        return true;
    }
}
