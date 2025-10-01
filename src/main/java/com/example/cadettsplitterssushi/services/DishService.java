package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.DishDTO;
import com.example.cadettsplitterssushi.entities.Booking;
import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.exceptions.EmptyFieldException;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.BookingRepository;
import com.example.cadettsplitterssushi.repositories.DishRepository;
import com.example.cadettsplitterssushi.util.CurrencyConverter;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishService implements DishServiceInterface{

    private final DishRepository dishRepository;
    private final BookingRepository bookingRepository;
    private final CurrencyConverter currencyConverter;

    private static final Logger logger = LoggerFactory.getLogger(DishService.class);

    @Autowired
    public DishService(DishRepository dishRepository, BookingRepository bookingRepository, CurrencyConverter currencyConverter) {
        this.dishRepository = dishRepository;
        this.bookingRepository = bookingRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public List<DishDTO> getAllDishes() {
        ArrayList<DishDTO> dishDTOList = new ArrayList<>();
        for (Dish dish : dishRepository.findAll()){
            dishDTOList.add(new DishDTO(dish.getId(), dish.getName(), dish.getPrice(), currencyConverter.convertFromSEKToEUR(dish.getPrice())));
        }
        return dishDTOList;
    }

    @Override
    public DishDTO createNewDish(Dish dish) {
        if (dish.getName().isEmpty() || dish.getName().isBlank()){
            throw new EmptyFieldException("dish", "name");
        }
        if (dish.getPrice() == 0){
            throw new IncorrectFormatException("Dish", "price", dish.getPrice(), "Integer, ex. '10' -or- Double, ex. 20.6, with value higher than 0");
        }
        Dish dishToSave = dishRepository.save(dish);
        return new DishDTO(dishToSave.getId(), dishToSave.getName(), dishToSave.getPrice(), currencyConverter.convertFromSEKToEUR(dishToSave.getPrice()));
    }

    @Override
    public void removeDishByID(long id) {
        Optional<Dish> dishToRemove = dishRepository.findById(id);
        if (dishToRemove.isEmpty()){
            throw new ResourceNotFoundException("Dish", "ID", id);
        } else {
            dishRepository.deleteById(id);
        }
    }

}
