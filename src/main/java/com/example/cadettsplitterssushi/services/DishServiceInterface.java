package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.DishDTO;
import com.example.cadettsplitterssushi.entities.Dish;

import java.util.List;

public interface DishServiceInterface {

    List<DishDTO> getAllDishes();
    DishDTO createNewDish(Dish dish);
    void removeDishByID(long id);
}
