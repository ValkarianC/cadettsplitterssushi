package com.example.cadettsplitterssushi.repositories;

import com.example.cadettsplitterssushi.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
