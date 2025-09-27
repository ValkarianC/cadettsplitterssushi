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

    public Dish() {
    }

    public Dish(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
}
