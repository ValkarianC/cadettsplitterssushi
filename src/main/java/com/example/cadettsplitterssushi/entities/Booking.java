package com.example.cadettsplitterssushi.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sushi_booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "booking_guest_number")
    private Long numberOfGuests;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "booking_meal",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    @Column(name = "booking_total_price")
    private Double totalPrice;

    @Column(name = "booking_is_cancelled")
    private boolean isCancelled;

    public Booking() {
    }

    public Booking(long id, Customer customer, Long numberOfGuests, Room room, List<Dish> dishes, Double totalPrice, boolean isCancelled) {
        this.id = id;
        this.customer = customer;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.dishes = dishes;
        this.totalPrice = totalPrice;
        this.isCancelled = isCancelled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
