package com.example.cadettsplitterssushi.controllers;

import com.example.cadettsplitterssushi.dto.BookingDTO;
import com.example.cadettsplitterssushi.entities.Booking;
import com.example.cadettsplitterssushi.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/wigellsushi")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //USER ENDPOINTS

    @PostMapping("/bookroom")
    public ResponseEntity<BookingDTO> bookRoom(@RequestBody BookingDTO booking, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(bookingService.createBooking(booking, userDetails), HttpStatus.CREATED);
    }

    public ResponseEntity<BookingDTO> cancelBooking(@RequestBody Booking booking){
        return null;
    }

    public ResponseEntity<List<BookingDTO>> getActiveBookings(){
        return null;
    }

    //ADMIN ENDPOINTS

    public ResponseEntity<List<BookingDTO>> listCancelledBookings(){
        return null;
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<BookingDTO>> listUpcomingBookings(){

        return new ResponseEntity<>(bookingService.listUpcomingBookings(),HttpStatus.OK);
    }
    public ResponseEntity<List<BookingDTO>> listPastBookings(){
        return null;
    }
}
