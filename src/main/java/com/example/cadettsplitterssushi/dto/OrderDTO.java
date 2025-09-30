package com.example.cadettsplitterssushi.dto;

import com.example.cadettsplitterssushi.entities.Dish;

import java.util.List;

public class OrderDTO {

    private Long id;
    private String customer;
    private List<Dish> dishes;
    private Double totalPriceSEK;
    private Double totalPriceEUR;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String customer, List<Dish> dishes, Double totalPriceSEK, Double totalPriceEUR) {
        this.id = id;
        this.customer = customer;
        this.dishes = dishes;
        this.totalPriceSEK = totalPriceSEK;
        this.totalPriceEUR = totalPriceEUR;
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

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Double getTotalPriceSEK() {
        return totalPriceSEK;
    }

    public void setTotalPriceSEK(Double totalPriceSEK) {
        this.totalPriceSEK = totalPriceSEK;
    }

    public Double getTotalPriceEUR() {
        return totalPriceEUR;
    }

    public void setTotalPriceEUR(Double totalPriceEUR) {
        this.totalPriceEUR = totalPriceEUR;
    }
}
