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
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/cancelbooking")
    public ResponseEntity<BookingDTO> cancelBooking(@RequestBody BookingDTO booking, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(bookingService.cancelBooking(booking, userDetails), HttpStatus.OK);
    }

    @GetMapping("/mybookings")
    public ResponseEntity<Object> getActiveBookings(@AuthenticationPrincipal UserDetails userDetails){
        List<BookingDTO> bookings = bookingService.listUserCurrentBookings(userDetails);
        if (bookings.isEmpty()){
            return new ResponseEntity<>("You currently have no active bookings in the system", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookings,HttpStatus.OK);
        }
    }


    //ADMIN ENDPOINTS

    @GetMapping("/listcancelled")
    public ResponseEntity<Object> listCancelledBookings(){
        List<BookingDTO> bookings = bookingService.listCancelledBookings();
        if (bookings.isEmpty()){
            return new ResponseEntity<>("No cancelled bookings currently in the system", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookings,HttpStatus.OK);
        }
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<Object> listUpcomingBookings(){
        List<BookingDTO> bookings = bookingService.listUpcomingBookings();
        if (bookings.isEmpty()){
            return new ResponseEntity<>("No upcoming bookings currently in the system", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookings,HttpStatus.OK);
        }

    }

    @GetMapping("/listpast")
    public ResponseEntity<Object> listPastBookings(){
        List<BookingDTO> bookings = bookingService.listPastBookings();
        if (bookings.isEmpty()){
            return new ResponseEntity<>("No past bookings currently in the system", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookings,HttpStatus.OK);
        }

    }
}
