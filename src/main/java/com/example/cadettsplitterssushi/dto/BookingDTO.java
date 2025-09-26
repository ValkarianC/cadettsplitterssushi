package com.example.cadettsplitterssushi.dto;

import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.entities.Room;

import java.util.List;

public class BookingDTO {

    private Long id;
    private String customer;
    private Long numberOfGuests;
    private Room room;
    private List<Dish> dishes;
    private String bookingDate;
    private String bookingTime;
    private Double priceSEK;
    private Double priceEUR;
    private Boolean isCancelled;

    public BookingDTO() {
    }

    public BookingDTO(Long id, String customer, Long numberOfGuests, Room room, List<Dish> dishes, String bookingDate, String bookingTime, Double priceSEK, Double priceEUR, Boolean isCancelled) {
        this.id = id;
        this.customer = customer;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.dishes = dishes;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.priceSEK = priceSEK;
        this.priceEUR = priceEUR;
        this.isCancelled = isCancelled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Long numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Double getPriceSEK() {
        return priceSEK;
    }

    public void setPriceSEK(Double priceSEK) {
        this.priceSEK = priceSEK;
    }

    public Double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(Double priceEUR) {
        this.priceEUR = priceEUR;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }
}
