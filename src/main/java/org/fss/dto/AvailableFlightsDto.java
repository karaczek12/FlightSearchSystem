package org.fss.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableFlightsDto {

    private Long id;

    @NotEmpty
    private String departureLocation;

    @NotEmpty
    private String arrivalLocation;

    @NotEmpty
    private int numberOfAvailableSeats;

    @NotEmpty
    private float costPerSeat;

    @NotEmpty
    private float yourCost;

    @NotEmpty
    private int numberOfSeatsChosen;

}
