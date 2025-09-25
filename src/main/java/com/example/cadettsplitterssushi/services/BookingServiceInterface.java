package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.BookingDTO;
import com.example.cadettsplitterssushi.entities.Booking;
import org.springframework.security.core.userdetails.UserDetails;

public interface BookingServiceInterface {
    BookingDTO createBooking(Booking booking, UserDetails userDetails);
}
