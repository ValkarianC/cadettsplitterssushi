package com.example.cadettsplitterssushi.controllers;

import com.example.cadettsplitterssushi.dto.OrderDTO;
import com.example.cadettsplitterssushi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/wigellsushi")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/orderfood")
    public ResponseEntity<OrderDTO> orderFood(@RequestBody OrderDTO order, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(orderService.createNewOrder(order, userDetails), HttpStatus.OK);
    }
}
