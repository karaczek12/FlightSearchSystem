package org.fss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "availableFlights")
public class AvailableFlights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "departure_location")
    private String departureLocation;

    @Column(nullable = false)
    private String arrivalLocation;

    @Column(nullable = false)
    private int numberOfAvailableSeats;

    @Column(nullable = false)
    private float costPerSeat;

    @Column(nullable = false)
    private boolean flightActive;

    @OneToMany(mappedBy = "availableFlights")
    private List<BookedFlights> bookedFlightsAssociation;

}
