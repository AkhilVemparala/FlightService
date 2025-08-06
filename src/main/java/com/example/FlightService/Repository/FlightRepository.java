package com.example.FlightService.Repository;

import com.example.FlightService.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Query("SELECT DISTINCT f.source FROM Flight f")
    List<String> findDistinctSources();

    @Query("SELECT DISTINCT f.destination FROM Flight f")
    List<String> findDistinctDestinations();

    @Query("SELECT f FROM Flight f WHERE f.source = :source AND f.destination = :destination AND f.flightAvailableDate = :journeyDate")
    List<Flight> findFlightsByRouteAndDate(String source, String destination, Date journeyDate);
}