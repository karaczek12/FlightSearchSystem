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
public class FlightsToBeBookedDto {

    @NotEmpty
    private long id;

    @NotEmpty
    private float yourCost;

    @NotEmpty
    private int numberOfSeatsChosen;

}
