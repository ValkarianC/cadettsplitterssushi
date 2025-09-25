package com.example.cadettsplitterssushi.dto;

import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.entities.Room;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class BookingDTO {

    private long id;
    private String customer;
    private long numberOfGuests;
    private Room room;
    private List<Dish> dishes;
    private Date date;
    private Time time;
    private double priceSEK;
    private double priceEUR;
    private boolean isCancelled;

    public BookingDTO() {
    }

    public BookingDTO(long id, String customer, long numberOfGuests, Room room, List<Dish> dishes, Date date, Time time, double priceSEK, double priceEUR, boolean isCancelled) {
        this.id = id;
        this.customer = customer;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.dishes = dishes;
        this.date = date;
        this.time = time;
        this.priceSEK = priceSEK;
        this.priceEUR = priceEUR;
        this.isCancelled = isCancelled;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public long getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(long numberOfGuests) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getPriceSEK() {
        return priceSEK;
    }

    public void setPriceSEK(double priceSEK) {
        this.priceSEK = priceSEK;
    }

    public double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(double priceEUR) {
        this.priceEUR = priceEUR;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
