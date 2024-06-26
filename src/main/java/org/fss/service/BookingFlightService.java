package org.fss.service;

import org.fss.dto.BookedFlightsDto;
import org.fss.dto.FlightsToBeBookedDto;

import java.util.List;

public interface BookingFlightService {

    void bookTheFlight(FlightsToBeBookedDto flightsToBeBookedDto, String userName);

    List<BookedFlightsDto> findAllBookedFlights(String userName);

}
