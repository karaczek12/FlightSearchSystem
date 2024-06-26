package org.fss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookedFlights")
public class BookedFlights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User users;

    @ManyToOne(fetch = FetchType.EAGER)
    private AvailableFlights availableFlights;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private float pricePayed;

    @Column(nullable = false)
    private int bookedNumOfSeats;
}
