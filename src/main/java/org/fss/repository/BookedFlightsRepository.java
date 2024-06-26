package org.fss.repository;


import org.fss.entity.BookedFlights;
import org.fss.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedFlightsRepository extends JpaRepository<BookedFlights, Long> {

    List<BookedFlights> findAllByUsers(User user);

}
