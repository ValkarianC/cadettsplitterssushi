package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.exceptions.EmptyFieldException;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DishService implements DishServiceInterface{

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> getAllDishes() {
        ArrayList<Dish> listOfDishes = (ArrayList<Dish>) dishRepository.findAll();
        for (Dish dish : listOfDishes){
            setDisplayPrice(dish);
        }
        return listOfDishes;
    }

    @Override
    public Dish createNewDish(Dish dish) {
        if (dish.getName().isEmpty() || dish.getName().isBlank()){
            throw new EmptyFieldException("dish", "name");
        }
        if (dish.getPrice().isEmpty() || dish.getPrice().isBlank()){
            throw new EmptyFieldException("dish", "price");
        }
        try {
            Double.parseDouble(dish.getPrice());
        } catch (NumberFormatException e){
            throw new IncorrectFormatException("Dish", "price", dish.getPrice(), "Integer, ex. '10' -or- Double, ex. 20.6");
        }
        Dish dishToSave = dishRepository.save(dish);
        setDisplayPrice(dishToSave);
        return dishToSave;
    }

    @Override
    public void removeDishByID(Long id) {
        Optional<Dish> dishToRemove = dishRepository.findById(id);
        if (dishToRemove.isEmpty()){
            throw new ResourceNotFoundException("Dish", "ID", id);
        } else {
            dishRepository.deleteById(id);
        }
    }


    private void setDisplayPrice(Dish dish){
        Double currentPrice = Double.valueOf(dish.getPrice());
        dish.setPrice(String.format("%.2f SEK | %.2f EUR", currentPrice, convertCurrency(currentPrice)));
    }

    public Double convertCurrency(Double price){
        RestClient restClient = RestClient.create();

        Map<String, Double> rates = (Map<String, Double>) restClient.get()
                .uri("https://api.frankfurter.dev/v1/latest?base=SEK&to=EUR")
                .retrieve()
                .body(Map.class).get("rates");
        Double rate = rates.get("EUR");

        return price * rate;
    }

}
