package org.fss.controller;

import org.fss.dto.AvailableFlightsDto;
import org.fss.dto.BookedFlightsDto;
import org.fss.dto.FlightSearchOptionDto;
import org.fss.dto.FlightsToBeBookedDto;
import org.fss.service.AvailableFlightsService;
import org.fss.service.BookingFlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class FlightSearchController {

    private final AvailableFlightsService availableFlightsService;
    private final BookingFlightService bookingFlightService;

    public FlightSearchController(AvailableFlightsService availableFlightsService, BookingFlightService bookingFlightService) {
        this.availableFlightsService = availableFlightsService;
        this.bookingFlightService = bookingFlightService;
    }

    // handler method to handle search page where user can choose location and number of seats
    @GetMapping("/searchOptions")
    public String searchOptions(Model model) {
        FlightSearchOptionDto flightSearchOption = availableFlightsService.findNumberOfSeatsAndDistinctDepartureLocation();
        model.addAttribute("depLocations", flightSearchOption.getDepartureLocations());
        model.addAttribute("numOfSeats", flightSearchOption.getListOfNumberOfSeats());
        return "searchFlights";
    }

    // handler method to show flights that user chose in search options
    @GetMapping("/searchFlights")
    public String searchFlights(Model model, @RequestParam String availableFlights, @RequestParam int availableSeats) {
        List<AvailableFlightsDto> flights = availableFlightsService.findAllAvailableFlightsByUserInput(availableFlights, availableSeats);
        model.addAttribute("availableFlights", flights);
        return "flights";
    }

    // handler method to handle booking a flight that the user chose
    @PostMapping("/bookFlight")
    public String bookFlight(@ModelAttribute FlightsToBeBookedDto flightsToBeBookedDto, Principal principal) {
        bookingFlightService.bookTheFlight(flightsToBeBookedDto, principal.getName());
        return "redirect:/bookedFlights";
    }

    // handler method to show a list of users booked flights
    @GetMapping("/bookedFlights")
    public String bookedFlights(Model model, Principal principal) {
        List<BookedFlightsDto> bookedFlights = bookingFlightService.findAllBookedFlights(principal.getName());
        model.addAttribute("bookedFlights", bookedFlights);
        return "bookedFlights";
    }

}
