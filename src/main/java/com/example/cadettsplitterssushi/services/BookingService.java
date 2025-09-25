package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.BookingDTO;
import com.example.cadettsplitterssushi.entities.Booking;
import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.BookingRepository;
import com.example.cadettsplitterssushi.repositories.RoomRepository;
import com.example.cadettsplitterssushi.util.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceInterface{

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final CurrencyConverter currencyConverter;

    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, CurrencyConverter currencyConverter) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public BookingDTO createBooking(Booking booking, UserDetails userDetails) {
        booking.setCustomer(userDetails.getUsername());

        if (booking.getRoom() == null){
            throw new IncorrectFormatException("Booking", "Room", booking.getRoom(), "room : { id:1 , name : Sushi Bar}, either ID or name can be used, ID overrides name.");
        }
        if (booking.getRoom().getId() == null && booking.getRoom().getName() == null){
            throw new ResourceNotFoundException("Booking", "Room", booking.getRoom());
        } else {
            Optional<Room> roomCheck = roomRepository.findById(booking.getRoom().getId());
            if (roomCheck.isPresent()){
                booking.setRoom(roomCheck.get());
            } else {
                List<Room> roomList = roomRepository.findRoomByName(booking.getRoom().getName());
                if (roomList.size() == 1) {
                    booking.setRoom(roomList.getFirst());
                }
            }
        }



        Booking bookingToSave = bookingRepository.save(booking);
        BookingDTO displayBooking = new BookingDTO();
        displayBooking.setCustomer(booking.getCustomer());
        displayBooking.setId(booking.getId());
        displayBooking.setRoom(booking.getRoom());
        return displayBooking;
    }


    private void checkBookingInformation(){

    }
}
