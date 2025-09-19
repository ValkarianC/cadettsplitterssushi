package com.example.cadettsplitterssushi.repositories;

import com.example.cadettsplitterssushi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {
}
