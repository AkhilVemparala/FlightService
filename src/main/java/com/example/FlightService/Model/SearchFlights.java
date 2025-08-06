package com.example.FlightService.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFlights {

    @NotEmpty(message = "Please enter the origin")
    private String source;

    @NotEmpty(message = "Please enter the destination")
    private String destination;

    @NotNull(message = "Journey date is mandatory")
    private Date journeyDate;

    private String fare;
    private String flightId;
    private String seatCount;
    private String departureTime;
    private String arrivalTime;
    private String airlines;
}
