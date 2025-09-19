package com.example.cadettsplitterssushi.repositories;

import com.example.cadettsplitterssushi.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
