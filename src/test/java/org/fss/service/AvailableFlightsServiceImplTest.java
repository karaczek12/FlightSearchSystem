package org.fss.service;

import org.fss.dto.AvailableFlightsDto;
import org.fss.dto.FlightSearchOptionDto;
import org.fss.entity.AvailableFlights;
import org.fss.repository.AvailableFlightsRepository;
import org.fss.service.impl.AvailableFlightsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class AvailableFlightsServiceImplTest {


    private final AvailableFlightsRepository availableFlightsRepository = Mockito.mock(AvailableFlightsRepository.class);

    @Test
    void test_findAllAvailableFlightsByUserInput_shouldReturnAListOfAvailableFlights() {
        AvailableFlightsServiceImpl availableFlightsService = new AvailableFlightsServiceImpl(availableFlightsRepository);

        AvailableFlights availableFlights = new AvailableFlights();
        availableFlights.setFlightActive(true);
        availableFlights.setNumberOfAvailableSeats(25);
        availableFlights.setId(1l);

        Mockito.when(availableFlightsRepository.findAllByDepartureLocation(any())).thenReturn(List.of(availableFlights));

        List<AvailableFlightsDto> actualFlightService = availableFlightsService.findAllAvailableFlightsByUserInput("dep", 25);

        assertThat(actualFlightService.get(0).getId()).isEqualTo(availableFlights.getId());
    }

    @Test
    void test_findNumberOfSeatsAndDistinctDepartureLocation_shouldReturnAFlightSearchOptionDtoObject() {
        AvailableFlightsServiceImpl availableFlightsService = new AvailableFlightsServiceImpl(availableFlightsRepository);

        AvailableFlights availableFlights = new AvailableFlights();
        availableFlights.setNumberOfAvailableSeats(3);

        List<String> distinctDep = new ArrayList<>();
        distinctDep.add("Krakow");

        Mockito.when(availableFlightsRepository.findDistinctDepartureLocation()).thenReturn(distinctDep);
        Mockito.when(availableFlightsRepository.findTopByOrderByNumberOfAvailableSeatsDesc()).thenReturn(Optional.of(availableFlights));

        FlightSearchOptionDto actualFlightService = availableFlightsService.findNumberOfSeatsAndDistinctDepartureLocation();

        assertThat(actualFlightService.getDepartureLocations()).isEqualTo(distinctDep);
        assertThat(actualFlightService.getListOfNumberOfSeats()).isEqualTo(List.of(1, 2, 3));
    }

}
