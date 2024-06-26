package org.fss.repository;

import org.fss.entity.AvailableFlights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AvailableFlightsRepository extends JpaRepository<AvailableFlights, Long> {

    List<AvailableFlights> findAllByDepartureLocation(String depLocation);

    @Query(value = "SELECT DISTINCT afs.departure_location FROM available_flights afs", nativeQuery = true)
    List<String> findDistinctDepartureLocation();

    Optional<AvailableFlights> findTopByOrderByNumberOfAvailableSeatsDesc();

    AvailableFlights findAllById(Long id);

}
