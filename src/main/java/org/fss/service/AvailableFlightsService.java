package org.fss.service;

import org.fss.dto.AvailableFlightsDto;
import org.fss.dto.FlightSearchOptionDto;

import java.util.List;

public interface AvailableFlightsService {

    List<AvailableFlightsDto> findAllAvailableFlightsByUserInput(String depName, int numOfSeats);

    FlightSearchOptionDto findNumberOfSeatsAndDistinctDepartureLocation();

}
