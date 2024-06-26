package org.fss.service.impl;

import org.fss.dto.BookedFlightsDto;
import org.fss.dto.FlightsToBeBookedDto;
import org.fss.entity.AvailableFlights;
import org.fss.entity.BookedFlights;
import org.fss.entity.User;
import org.fss.repository.AvailableFlightsRepository;
import org.fss.repository.BookedFlightsRepository;
import org.fss.repository.UserRepository;
import org.fss.service.BookingFlightService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingFlightServiceImpl implements BookingFlightService {

    private final BookedFlightsRepository bookedFlightsRepository;
    private final AvailableFlightsRepository availableFlightsRepository;
    private final UserRepository userRepository;

    public BookingFlightServiceImpl(BookedFlightsRepository bookedFlightsRepository,
                                    AvailableFlightsRepository availableFlightsRepository, UserRepository userRepository) {
        this.bookedFlightsRepository = bookedFlightsRepository;
        this.availableFlightsRepository = availableFlightsRepository;
        this.userRepository = userRepository;
    }

    //A function that saves a booked flight to BookedFlights repository
    @Override
    public void bookTheFlight(FlightsToBeBookedDto flightsToBeBookedDto, String userName) {
        User user = getCurrentUser(userName);
        AvailableFlights availableFlights = getAvailableFlightCheckAndUpdate(flightsToBeBookedDto.getId(),
                flightsToBeBookedDto.getNumberOfSeatsChosen());

        BookedFlights bookedFlight = new BookedFlights();
        bookedFlight.setAvailableFlights(availableFlights);
        bookedFlight.setBookingDate(LocalDateTime.now());
        bookedFlight.setUsers(user);
        bookedFlight.setPricePayed(flightsToBeBookedDto.getYourCost());
        bookedFlight.setBookedNumOfSeats(flightsToBeBookedDto.getNumberOfSeatsChosen());

        bookedFlightsRepository.save(bookedFlight);

    }

    //A function that finds all booked flights for a current user
    @Override
    public List<BookedFlightsDto> findAllBookedFlights(String userName) {
        User user = getCurrentUser(userName);
        List<BookedFlights> bookedFlights = bookedFlightsRepository.findAllByUsers(user);
        return bookedFlights.stream().map(this::mapToBookedFlightsToDto).collect(Collectors.toList());
    }

    //A function that gets current user
    private User getCurrentUser(String userName) {
        return userRepository.findByEmail(userName);
    }

    //A function that gets Available Flight by its id from repository
    private AvailableFlights getAvailableFlight(Long id) {
        Optional<AvailableFlights> availableFlights = availableFlightsRepository.findById(id);

        return availableFlights.orElseThrow();
    }

    //A function that gets available flight based on id and updates its number of seats
    private AvailableFlights getAvailableFlightCheckAndUpdate(Long id, int numOfSeatsChosen) {
        AvailableFlights availableFlights = getAvailableFlight(id);
        availableFlights.setNumberOfAvailableSeats(availableFlights.getNumberOfAvailableSeats() - numOfSeatsChosen);

        if (availableFlights.getNumberOfAvailableSeats() <= 0) {
            availableFlights.setFlightActive(false);
        }

        availableFlightsRepository.save(availableFlights);

        return availableFlights;
    }

    //A function that maps BookedFlights class to BookedFlightsDto class
    private BookedFlightsDto mapToBookedFlightsToDto(BookedFlights bookedFlights) {
        BookedFlightsDto bookedFlightsDto = new BookedFlightsDto();
        bookedFlightsDto.setBookingDate(bookedFlights.getBookingDate());
        bookedFlightsDto.setYourCost(bookedFlights.getPricePayed());
        bookedFlightsDto.setArrivalLocation(bookedFlights.getAvailableFlights().getArrivalLocation());
        bookedFlightsDto.setDepartureLocation(bookedFlights.getAvailableFlights().getDepartureLocation());
        bookedFlightsDto.setNumberOfSeatsChosen(bookedFlights.getBookedNumOfSeats());
        bookedFlightsDto.setCostPerSeat(bookedFlights.getAvailableFlights().getCostPerSeat());
        return bookedFlightsDto;
    }
}
