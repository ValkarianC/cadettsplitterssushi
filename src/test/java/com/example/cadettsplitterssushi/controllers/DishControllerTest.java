package com.example.cadettsplitterssushi.controllers;

import com.example.cadettsplitterssushi.entities.Dish;
import com.example.cadettsplitterssushi.exceptions.EmptyFieldException;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.repositories.DishRepository;
import com.example.cadettsplitterssushi.services.DishService;
import com.example.cadettsplitterssushi.util.CurrencyConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class DishControllerTest {

    @Mock
    private DishRepository dishRepositoryMock;
    @Mock
    private CurrencyConverter currencyConverterMock;
    @InjectMocks
    private DishService dishService;

    private DishController dishController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        dishController = new DishController(dishService);
        mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void getDishes_ShouldReturnAllDishesInList() throws Exception {
        List<Dish> dishes = Arrays.asList(
                new Dish(1L, "Dish One", 150.0, null, null),
                new Dish(2L, "Dish Two", 250.0, null, null),
                new Dish(3L, "Dish Three", 75.0, null, null)
        );
        when(dishRepositoryMock.findAll()).thenReturn(dishes);
        when(currencyConverterMock.convertFromSEKToEUR(ArgumentMatchers.anyDouble())).thenReturn(500.0);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellsushi/dishes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].dishName").value("Dish One"))
                .andExpect(jsonPath("$[1].dishName").value("Dish Two"))
                .andExpect(jsonPath("$[2].dishName").value("Dish Three"))
                .andExpect(jsonPath("$[2].priceSEK").value(75.0))
                .andExpect(jsonPath("$[2].priceEUR").value(500.0));
        verify(dishRepositoryMock).findAll();
        verify(currencyConverterMock, times(3)).convertFromSEKToEUR(ArgumentMatchers.anyDouble());
    }

    @Test
    void addNewDish_ShouldReturnNewDishDTO() throws Exception {
        Dish dish = new Dish(1L, "New Dish", 200.0, null,null);
        String json = objectMapper.writeValueAsString(dish);
        when(dishRepositoryMock.save(ArgumentMatchers.isA(Dish.class))).thenReturn(dish);
        when(currencyConverterMock.convertFromSEKToEUR(anyDouble())).thenReturn(500.0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/wigellsushi/add-dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dishId").value(1L))
                .andExpect(jsonPath("$.dishName").value("New Dish"))
                .andExpect(jsonPath("$.priceSEK").value(200.0))
                .andExpect(jsonPath("$.priceEUR").value(500.0));
        verify(dishRepositoryMock).save(isA(Dish.class));
        verify(currencyConverterMock).convertFromSEKToEUR(ArgumentMatchers.anyDouble());
    }

    @Test
    void removeDish() throws Exception {
        Dish dish = new Dish(1L, "Removed Dish", 200.0, null,null);


        when(dishRepositoryMock.findById(1L)).thenReturn(Optional.of(dish));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/wigellsushi/remdish/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dish with ID : 1 has been removed"));

        verify(dishRepositoryMock).findById(1L);
    }

    @Test
    void getAllDishesShouldReturnMessageWhenDatabaseEmpty() throws Exception {
        List<Dish> dishes = new ArrayList<>();
        when(dishRepositoryMock.findAll()).thenReturn(dishes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellsushi/dishes"))
                .andExpect(status().isOk())
                .andExpect(content().string("No dishes in the database."));

        verify(dishRepositoryMock).findAll();
    }

    @Test
    void addNewDish_ShouldReturnEmptyFieldExceptionFromServiceLayerWhenNameIsEmpty() throws Exception {
        Dish dish = new Dish(1L, "", 200.0, null,null);
        String json = objectMapper.writeValueAsString(dish);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/wigellsushi/add-dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldException))
                .andExpect(result -> assertEquals("Cannot create a dish object with an empty name field.", result.getResolvedException().getMessage()))
        ;
    }
    @Test
    void addNewDish_ShouldReturnIncorrectFormatExceptionWhenPriceIs0() throws Exception {
        Dish dish = new Dish(1L, "New Dish", 0, null,null);
        String json = objectMapper.writeValueAsString(dish);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/wigellsushi/add-dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectFormatException))
                .andExpect(result -> assertEquals("Dish with price value '0.0' is invalid. Please enter the value in the following format: Integer, ex. '10' -or- Double, ex. 20.6, with value higher than 0",
                        result.getResolvedException().getMessage()));
    }
}