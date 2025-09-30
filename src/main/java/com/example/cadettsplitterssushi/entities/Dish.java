package com.example.cadettsplitterssushi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "sushi_dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private long id;

    @Column(length = 50, nullable = false, name = "dish_name")
    private String name;

    @Column(name = "dish_price")
    private double price;

    @ManyToMany(mappedBy = "dishes")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Booking> bookings;

    @ManyToMany(mappedBy = "dishes")
            @OnDelete(action = OnDeleteAction.CASCADE)
    List<Order> orders;

    public Dish() {
    }

    public Dish(long id, String name, double price, List<Booking> bookings, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bookings = bookings;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
