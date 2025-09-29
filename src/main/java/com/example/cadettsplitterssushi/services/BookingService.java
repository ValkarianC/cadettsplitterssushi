package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.BookingDTO;
import com.example.cadettsplitterssushi.entities.Booking;
import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.InvalidRequestException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.BookingRepository;
import com.example.cadettsplitterssushi.repositories.DishRepository;
import com.example.cadettsplitterssushi.repositories.RoomRepository;
import com.example.cadettsplitterssushi.util.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class BookingService implements BookingServiceInterface{

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final DishRepository dishRepository;
    private final CurrencyConverter currencyConverter;

    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, DishRepository dishRepository, CurrencyConverter currencyConverter) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.dishRepository = dishRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public BookingDTO createBooking(BookingDTO booking, UserDetails userDetails) {
        verifyBookingInformation(booking);
        booking.setCustomer(userDetails.getUsername());
        booking.setPriceSEK(calculateTotalPrice(booking.getDishes()));
        booking.setPriceEUR(currencyConverter.convertFromSEKToEUR(booking.getPriceSEK()));
        booking.setCancelled(false);

        Booking bookingToSave = bookingRepository.save(new Booking(
                booking.getCustomer(),
                booking.getNumberOfGuests(),
                booking.getRoom(),
                booking.getDishes(),
                Date.valueOf(booking.getBookingDate()),
                Time.valueOf(booking.getBookingTime()),
                booking.getPriceSEK(),
                false
        ));
        booking.setId(bookingToSave.getId());
        return booking;
    }

    @Override
    public BookingDTO cancelBooking(BookingDTO bookingDTO, UserDetails userDetails) {

        if (bookingDTO.getId() == null) {
            throw new ResourceNotFoundException("Booking", "ID", bookingDTO.getId());
        }
        Optional<Booking> bookingToCancel = bookingRepository.findById(bookingDTO.getId());
        if (bookingToCancel.isEmpty()){
            throw new ResourceNotFoundException("Booking", "ID", bookingDTO.getId());
        } else {
            if (!bookingToCancel.get().getCustomer().equals(userDetails.getUsername())) {
                throw new InvalidRequestException("Users are only authorised to cancel booking they have made.");
            } else {
                if (bookingToCancel.get().isCancelled()){
                    throw new InvalidRequestException("Booking has already been cancelled.");
                }
                if (bookingToCancel.get().getBookingDate().before(Date.valueOf(LocalDate.now().plusWeeks(1)))){
                    throw new InvalidRequestException("Bookings need to be cancelled with at least 1 weeks notice.");
                }
                bookingToCancel.get().setCancelled(true);
                return createDisplayDTO(bookingRepository.save(bookingToCancel.get()));
            }
        }
    }

    @Override
    public List<BookingDTO> listUserCurrentBookings(UserDetails userDetails) {
        List<Booking> bookingList = bookingRepository.findBookingsByCustomer(userDetails.getUsername());
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList){
            bookingDTOList.add(createDisplayDTO(booking));
        }
        return bookingDTOList;
    }

    @Override
    public List<BookingDTO> listCancelledBookings() {
        List<Booking> bookingList = bookingRepository.findBookingsByCancelled(true);
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList){
            bookingDTOList.add(createDisplayDTO(booking));
        }
        return bookingDTOList;
    }


    @Override
    public List<BookingDTO> listUpcomingBookings() {
        List<Booking> bookingList = bookingRepository.findBookingsByCancelled(false);
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList){
            if (booking.getBookingDate().after(Date.valueOf(LocalDate.now()))){
                bookingDTOList.add(createDisplayDTO(booking));
            }
            if (booking.getBookingDate().equals(Date.valueOf(LocalDate.now())) && booking.getBookingTime().after(Time.valueOf(LocalTime.now()))){
                bookingDTOList.add(createDisplayDTO(booking));
            }
        }
        return bookingDTOList;
    }

    @Override
    public List<BookingDTO> listPastBookings() {
        List<Booking> bookingList = bookingRepository.findBookingsByCancelled(false);
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList){
            if (booking.getBookingDate().before(Date.valueOf(LocalDate.now()))){
                bookingDTOList.add(createDisplayDTO(booking));
            }
            if (booking.getBookingDate().equals(Date.valueOf(LocalDate.now())) && booking.getBookingTime().before(Time.valueOf(LocalTime.now()))){
                bookingDTOList.add(createDisplayDTO(booking));
            }
        }
        return bookingDTOList;    }

    private BookingDTO createDisplayDTO(Booking booking){
        return  new BookingDTO(
                booking.getId(),
                booking.getCustomer(),
                booking.getNumberOfGuests(),
                booking.getRoom(),
                booking.getDishes(),
                booking.getBookingDate().toString(),
                booking.getBookingTime().toString(),
                booking.getTotalPrice(),
                currencyConverter.convertFromSEKToEUR(booking.getTotalPrice()),
                booking.isCancelled()
        );
    }


    private void verifyBookingInformation(BookingDTO booking){
        String dateRegex = "(19|20)([0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
        String timeRegex = "(0[1-9]|1[0-9]|2[0-4])([0-5][0-9]|:[0-5][0-9]:[0-5][0-9])";
        String timeRegexSimple = "(0[1-9]|1[0-9]|2[0-4])([0-5][0-9])";


        if (booking.getRoom() == null || booking.getRoom().getId() == null){
            throw new IncorrectFormatException("Booking", "room", booking.getRoom(), "room : { id:1 , name:Sushi Bar} - only Room ID is required.");
        }
        Optional<Room> roomCheck = roomRepository.findById(booking.getRoom().getId());
        if (roomCheck.isEmpty()){
            throw new ResourceNotFoundException("Room", "Id", booking.getRoom().getId());
        } else {
            if (!roomCheck.get().getAvailable()){
                throw new InvalidRequestException("Room with ID: "+ roomCheck.get().getId() + " is currently unavailable");
            }
            booking.setRoom(roomCheck.get());
        }

        if (booking.getDishes() == null || booking.getDishes().isEmpty()){
            throw new IncorrectFormatException("Booking", "dishes", booking.getDishes(), "dishes: [{id:1, name:Sashimi}, {id:1, name:Sashimi}, ...] - At least one dish must be input, only Dish ID is required.");
        }
        for (int i = 0; i < booking.getDishes().size(); i++) {
            Optional<Dish> dishCheck = dishRepository.findById(booking.getDishes().get(i).getId());
            if (dishCheck.isEmpty()){
                throw new ResourceNotFoundException("Dish", "Id", booking.getDishes().get(i).getId());
            } else {
                booking.getDishes().set(i, dishCheck.get());
            }
        }

        if (booking.getNumberOfGuests() == null){
            throw new ResourceNotFoundException("Booking", "numberOfGuests", booking.getNumberOfGuests());
        }
        if (booking.getNumberOfGuests() > booking.getRoom().getGuestCapacity()){
            throw new InvalidRequestException("Number of guests is larger than room's available capacity");
        }

        if (booking.getBookingDate() == null || !Pattern.matches(dateRegex, booking.getBookingDate())) {
            throw new IncorrectFormatException("Booking", "bookingDate", booking.getBookingDate(), "YYYY-MM-DD - Date must be a valid date set on/after today's date. ");
        }
        if (booking.getBookingTime() == null || !Pattern.matches(timeRegex, booking.getBookingTime())){
            throw new IncorrectFormatException("Booking", "bookingTime", booking.getBookingTime(), "HH:MM:SS(17:00:00) -or- HH00(1700) - Time must be set at least one hour in the future. Second values are not taken into consideration");
        }
        Date dateCheck = Date.valueOf(booking.getBookingDate());
        Time timeCheck = Time.valueOf(Pattern.matches(timeRegexSimple, booking.getBookingTime()) ?
                booking.getBookingTime().substring(0,2)+":"+booking.getBookingTime().substring(2)+":00" :
                booking.getBookingTime());

        if (dateCheck.before(Date.valueOf(LocalDate.now()))){
            throw new IncorrectFormatException("Booking", "bookingDate", booking.getBookingDate(), "YYYY-MM-DD - Date must be a valid date set on/after today's date. ");
        }

        if (dateCheck.equals(Date.valueOf(LocalDate.now()))){
            System.out.println("Today");
            if (Time.valueOf(LocalTime.now().plusHours(1)).after(timeCheck)){
                System.out.println("Too late");
                throw new IncorrectFormatException("Booking", "bookingTime", booking.getBookingTime(), "HH:MM:SS(17:00:00) -or- HH00(1700) - Time must be set at least one hour in the future. Second values are not taken into consideration");
            }
        }
        booking.setBookingDate(dateCheck.toString());
        booking.setBookingTime(timeCheck.toString());
    }

    private double calculateTotalPrice(List<Dish> dishes){
        double totalPrice = 0;

        for (Dish dish : dishes){
            totalPrice += dish.getPrice();
        }

        return totalPrice;
    }
}
