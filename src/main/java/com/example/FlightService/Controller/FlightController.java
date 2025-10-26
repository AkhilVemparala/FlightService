package com.example.FlightService.Controller;

import com.example.FlightService.Entity.Flight;
import com.example.FlightService.Model.FlightResponseModel;
import com.example.FlightService.Model.SearchFlights;
import com.example.FlightService.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/sources")
    public ResponseEntity<List<String>> getSources() {
        return ResponseEntity.ok(flightService.getSources());
    }

    @GetMapping("/destinations")
    public ResponseEntity<List<String>> getDestinations() {
        return ResponseEntity.ok(flightService.getDestinations());
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String flightId) {
        return ResponseEntity.ok(flightService.getFlightById(flightId));
    }

    @GetMapping("/{source}/{destination}/{journeyDate}")
    public ResponseEntity<List<SearchFlights>> searchFlights(
            @PathVariable String source,
            @PathVariable String destination,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date journeyDate) {

        return ResponseEntity.ok(flightService.searchFlights(source, destination, journeyDate));
    }

    @PutMapping("/{flightId}/{seats}")
    public ResponseEntity<FlightResponseModel> updateSeatCount(@PathVariable String flightId, @PathVariable int seats) {
        flightService.updateSeatCount(flightId, seats);
        return new ResponseEntity<>(new FlightResponseModel(true, "Seat count updated"), HttpStatus.OK);
    }

    @PostMapping("/addFlight")
    public ResponseEntity<FlightResponseModel> addFlights(@RequestBody Flight flight) {
        flightService.addFlight(flight);
        return new ResponseEntity<>(new FlightResponseModel(true, "Flight details added"), HttpStatus.OK);
    }
}
