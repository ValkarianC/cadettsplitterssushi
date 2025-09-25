package com.example.cadettsplitterssushi.dto;

import java.text.DecimalFormat;

public class DishDTO {

    private long dishId;
    private String dishName;
    private double priceSEK;
    private double priceEUR;

    public DishDTO() {
    }

    public DishDTO(long dishId, String dishName, double priceSEK, double priceEUR) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.dishId = dishId;
        this.dishName = dishName;
        this.priceSEK = Double.valueOf(decimalFormat.format(priceSEK));
        this.priceEUR = Double.valueOf(decimalFormat.format(priceEUR));
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
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
}
