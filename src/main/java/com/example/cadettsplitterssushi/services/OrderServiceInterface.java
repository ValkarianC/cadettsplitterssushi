package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.dto.OrderDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrderServiceInterface {
    OrderDTO createNewOrder(OrderDTO order, UserDetails userDetails);
}
