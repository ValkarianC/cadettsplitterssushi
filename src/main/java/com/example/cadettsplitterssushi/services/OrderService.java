package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.OrderDTO;
import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.entities.Order;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.DishRepository;
import com.example.cadettsplitterssushi.repositories.OrderRepository;
import com.example.cadettsplitterssushi.util.CurrencyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface{

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final CurrencyConverter currencyConverter;

    private static final Logger logger = LoggerFactory.getLogger("LogFile");

    @Autowired
    public OrderService(OrderRepository orderRepository, DishRepository dishRepository, CurrencyConverter currencyConverter) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public OrderDTO createNewOrder(OrderDTO order, UserDetails userDetails) {
        verifyOrderDetails(order);
        order.setCustomer(userDetails.getUsername());
        order.setTotalPriceSEK(calculateTotalPrice(order.getDishes()));
        order.setTotalPriceEUR(currencyConverter.convertFromSEKToEUR(order.getTotalPriceSEK()));

        Order orderToSave = orderRepository.save(new Order(
                order.getCustomer(),
                order.getDishes(),
                order.getTotalPriceSEK()
                ));
        order.setId(orderToSave.getId());
        logger.info("User has made a new order with total price: {} SEK|{} EUR. Order ID: {}", order.getTotalPriceSEK(), order.getTotalPriceEUR(), order.getId());
        return order;
    }

    private void verifyOrderDetails(OrderDTO order){

        if (order.getDishes() == null || order.getDishes().isEmpty()){
            throw new IncorrectFormatException("Order", "dishes", order.getDishes(), "dishes: [{id:1, name:Sashimi}, {id:1, name:Sashimi}, ...] - At least one dish must be input, only Dish ID is required.");
        }
        for (int i = 0; i < order.getDishes().size(); i++) {
            Optional<Dish> dishCheck = dishRepository.findById(order.getDishes().get(i).getId());
            if (dishCheck.isEmpty()){
                throw new ResourceNotFoundException("Dish", "ID", order.getDishes().get(i).getId());
            } else {
                order.getDishes().set(i, dishCheck.get());
            }
        }



    }

    private double calculateTotalPrice(List<Dish> dishes){
        double totalPrice = 0;

        for (Dish dish : dishes){
            totalPrice += dish.getPrice();
        }

        return totalPrice;
    }
}
