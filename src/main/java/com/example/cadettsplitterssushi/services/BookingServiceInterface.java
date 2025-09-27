package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.BookingDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BookingServiceInterface {
    BookingDTO createBooking(BookingDTO booking, UserDetails userDetails);
    List<BookingDTO> listUpcomingBookings();
}
