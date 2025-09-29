package com.example.cadettsplitterssushi.entities;

import jakarta.persistence.*;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "sushi_booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long id;

    @Column(name = "booking_customer", length = 50, nullable = false)
    private String customer;

    @Column(name = "booking_guest_number")
    private Long numberOfGuests;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToMany()
    @JoinTable(
            name = "booking_meal",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "booking_time")
    private Time bookingTime;

    @Column(name = "booking_total_price")
    private Double totalPrice;

    @Column(name = "booking_is_cancelled")
    private boolean cancelled;

    public Booking() {
    }

    public Booking(long id, String customer, Long numberOfGuests, Room room, List<Dish> dishes, Date bookingDate, Time bookingTime, Double totalPrice, boolean cancelled) {
        this.id = id;
        this.customer = customer;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.dishes = dishes;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.totalPrice = totalPrice;
        this.cancelled = cancelled;
    }

    public Booking(String customer, Long numberOfGuests, Room room, List<Dish> dishes, Date bookingDate, Time bookingTime, Double totalPrice, boolean cancelled) {
        this.customer = customer;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.dishes = dishes;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.totalPrice = totalPrice;
        this.cancelled = cancelled;
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

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Time getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Time bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
