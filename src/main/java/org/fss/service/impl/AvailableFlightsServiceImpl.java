package org.fss.service.impl;

import org.fss.dto.AvailableFlightsDto;
import org.fss.dto.FlightSearchOptionDto;
import org.fss.entity.AvailableFlights;
import org.fss.repository.AvailableFlightsRepository;
import org.fss.service.AvailableFlightsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AvailableFlightsServiceImpl implements AvailableFlightsService {

    private final AvailableFlightsRepository availableFlightsRepository;

    public AvailableFlightsServiceImpl(AvailableFlightsRepository availableFlightsRepository) {
        this.availableFlightsRepository = availableFlightsRepository;
    }

    //A function that finds all available flight and filter it out based on user input
    @Override
    public List<AvailableFlightsDto> findAllAvailableFlightsByUserInput(String depName, int numOfSeats) {
        List<AvailableFlights> flights = availableFlightsRepository.findAllByDepartureLocation(depName);
        return flights.stream()
                //checks if available seat on that flight is greater or equals to numbers of seats user choosed
                .filter(flight -> flight.getNumberOfAvailableSeats() >= numOfSeats)
                //checks if the flight is active
                .filter(AvailableFlights::isFlightActive)
                .map(flight -> mapToFlights(flight, numOfSeats))
                .collect(Collectors.toList());
    }

    //A function that finds a distinct flight based on location and generates a list of integers based of available spaces
    @Override
    public FlightSearchOptionDto findNumberOfSeatsAndDistinctDepartureLocation() {
        List<String> distinctDepartures = availableFlightsRepository.findDistinctDepartureLocation();
        FlightSearchOptionDto flightSearchOptionDto = new FlightSearchOptionDto();
        flightSearchOptionDto.setDepartureLocations(distinctDepartures);
        flightSearchOptionDto.setListOfNumberOfSeats(IntStream.range(1, getMaxNumberOfSeats() + 1).boxed().toList());
        return flightSearchOptionDto;
    }

    //A function that gets and returns the biggest available in database number of seats
    private int getMaxNumberOfSeats() {
        return availableFlightsRepository.findTopByOrderByNumberOfAvailableSeatsDesc()
                .map(AvailableFlights::getNumberOfAvailableSeats).orElse(0);
    }

    //A function that maps AvailableFlight class to AvailableFlightsDto class
    private AvailableFlightsDto mapToFlights(AvailableFlights availableFlights, int numOfSeats) {

        AvailableFlightsDto availableFlightsDto = new AvailableFlightsDto();

        availableFlightsDto.setId(availableFlights.getId());
        availableFlightsDto.setArrivalLocation(availableFlights.getArrivalLocation());
        availableFlightsDto.setCostPerSeat(availableFlights.getCostPerSeat());
        availableFlightsDto.setDepartureLocation(availableFlights.getDepartureLocation());
        availableFlightsDto.setNumberOfAvailableSeats(availableFlights.getNumberOfAvailableSeats());
        availableFlightsDto.setNumberOfSeatsChosen(numOfSeats);
        availableFlightsDto.setYourCost(availableFlights.getCostPerSeat() * numOfSeats);

        return availableFlightsDto;

    }
}
