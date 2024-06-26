package org.fss.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookedFlightsDto {

    @NotEmpty
    private String departureLocation;

    @NotEmpty
    private String arrivalLocation;

    @NotEmpty
    private float costPerSeat;

    @NotEmpty
    private float yourCost;

    @NotEmpty
    private int numberOfSeatsChosen;

    @NotEmpty
    private LocalDateTime bookingDate;
}
