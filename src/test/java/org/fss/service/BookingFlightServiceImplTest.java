package org.fss.service;

import org.fss.dto.BookedFlightsDto;
import org.fss.dto.FlightsToBeBookedDto;
import org.fss.entity.AvailableFlights;
import org.fss.entity.BookedFlights;
import org.fss.repository.AvailableFlightsRepository;
import org.fss.repository.BookedFlightsRepository;
import org.fss.repository.UserRepository;
import org.fss.service.impl.BookingFlightServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingFlightServiceImplTest {

    private final BookedFlightsRepository bookedFlightsRepository = Mockito.mock(BookedFlightsRepository.class);
    private final AvailableFlightsRepository availableFlightsRepository = Mockito.mock(AvailableFlightsRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);


    @Test
    void test_bookTheFlight_shouldCallFindByEmail() {
        BookingFlightServiceImpl bookingFlightService = new BookingFlightServiceImpl(bookedFlightsRepository, availableFlightsRepository, userRepository);
        FlightsToBeBookedDto beBookedDto = new FlightsToBeBookedDto();
        AvailableFlights availableFlights = new AvailableFlights();
        when(availableFlightsRepository.findById(any())).thenReturn(Optional.of(availableFlights));
        bookingFlightService.bookTheFlight(beBookedDto, "user");
        verify(bookedFlightsRepository, times(1)).save(any());
    }

    @Test
    void test_findAllBookedFlights_shouldReturnListOfBookedFlightsDto() {
        BookingFlightServiceImpl bookingFlightService = new BookingFlightServiceImpl(bookedFlightsRepository, availableFlightsRepository, userRepository);
        BookedFlights bookedFlights = new BookedFlights();
        AvailableFlights availableFlights = new AvailableFlights();
        availableFlights.setArrivalLocation("Location");
        bookedFlights.setBookedNumOfSeats(2);
        bookedFlights.setAvailableFlights(availableFlights);
        when(bookedFlightsRepository.findAllByUsers(any())).thenReturn(List.of(bookedFlights));
        List<BookedFlightsDto> actualBookedFlights = bookingFlightService.findAllBookedFlights("user");
        assertThat(actualBookedFlights.get(0).getNumberOfSeatsChosen()).isEqualTo(bookedFlights.getBookedNumOfSeats());
    }
}
