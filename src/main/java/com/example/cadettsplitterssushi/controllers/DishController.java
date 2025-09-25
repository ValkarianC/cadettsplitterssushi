package com.example.cadettsplitterssushi.controllers;

import com.example.cadettsplitterssushi.dto.DishDTO;
import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/wigellsushi")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    // ADMIN & USER ENDPOINTS
    @GetMapping("/dishes")
    public ResponseEntity<Object> getDishes(){
        List<DishDTO> availableDishes = dishService.getAllDishes();
        if (availableDishes.isEmpty()){
            return new ResponseEntity<>("No dishes in the database.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(availableDishes, HttpStatus.OK);
        }
    }

    // ADMIN ENDPOINTS
    @PostMapping("/add-dish")
    public ResponseEntity<DishDTO> addNewDish(@RequestBody Dish dish){
        return new ResponseEntity<>(dishService.createNewDish(dish), HttpStatus.CREATED);
    }

    @DeleteMapping("/remdish/{id}")
    public ResponseEntity<String> removeDish(@PathVariable("id")long id){
        dishService.removeDishByID(id);
        return new ResponseEntity<>("Dish with ID : " + id + " has been removed", HttpStatus.OK);
    }
}
