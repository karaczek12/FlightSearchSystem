package org.fss.controller;

import org.fss.dto.AvailableFlightsDto;
import org.fss.dto.BookedFlightsDto;
import org.fss.dto.FlightSearchOptionDto;
import org.fss.dto.FlightsToBeBookedDto;
import org.fss.service.AvailableFlightsService;
import org.fss.service.BookingFlightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FlightSearchController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class FlightSearchControllerTest {

    @MockBean
    private AvailableFlightsService availableFlightsService;

    @MockBean
    private BookingFlightService bookingFlightService;

    @Mock
    private Principal principal;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_searchOptions_shouldPass_WhenOptionsAreAdded() throws Exception {
        FlightSearchOptionDto flightSearchOptionDto = new FlightSearchOptionDto();
        flightSearchOptionDto.setDepartureLocations(List.of("Krakow", "Warsaw"));
        flightSearchOptionDto.setListOfNumberOfSeats(List.of(1, 2, 3, 4, 5));

        Mockito.when(availableFlightsService.findNumberOfSeatsAndDistinctDepartureLocation()).thenReturn(flightSearchOptionDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/searchOptions"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchFlights"))
                .andExpect(model().attributeExists("depLocations"))
                .andExpect(model().attributeExists("numOfSeats"));
    }

    @Test
    public void test_searchOptions_shouldNotHaveAttribute() throws Exception {

        FlightSearchOptionDto flightSearchOptionDto = new FlightSearchOptionDto();
        flightSearchOptionDto.setDepartureLocations(List.of("Krakow", "Warsaw"));

        Mockito.when(availableFlightsService.findNumberOfSeatsAndDistinctDepartureLocation()).thenReturn(flightSearchOptionDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchOptions"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchFlights"))
                .andExpect(model().attributeExists("depLocations"))
                .andExpect(model().attributeDoesNotExist("numOfSeats"));
    }

    @Test
    public void test_searchFlights_shouldPassWhenParamsAreAdded() throws Exception {

        AvailableFlightsDto availableFlightsDto = new AvailableFlightsDto();
        availableFlightsDto.setId(1L);

        Mockito.when(availableFlightsService.findAllAvailableFlightsByUserInput("Krakow", 25)).thenReturn(List.of(availableFlightsDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/searchFlights")
                        .param("availableFlights", "Krakow")
                        .param("availableSeats", "25"))
                .andExpect(status().isOk())
                .andExpect(view().name("flights"))
                .andExpect(model().attributeExists("availableFlights"));
    }

    @Test
    public void test_bookFlight_shouldPassWhenModelAttributeIsAdded() throws Exception {
        Mockito.when(principal.getName()).thenReturn("name");
        mockMvc.perform(MockMvcRequestBuilders.post("/bookFlight")
                        .flashAttr("entity", new FlightsToBeBookedDto())
                        .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bookedFlights"));
    }

    @Test
    public void test_bookedFlights_shouldPassWhenModelIsAdded() throws Exception {

        BookedFlightsDto bookedFlightsDto = new BookedFlightsDto();

        Mockito.when(bookingFlightService.findAllBookedFlights(any())).thenReturn(List.of(bookedFlightsDto));
        Mockito.when(principal.getName()).thenReturn("name");
        mockMvc.perform(MockMvcRequestBuilders.get("/bookedFlights")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("bookedFlights"))
                .andExpect(model().attributeExists("bookedFlights"));
    }
}

