package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.exceptions.OrderNotFoundException;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    OrderDto getOrderById(@PathVariable long orderId) {
        return orderService.getOrderById(orderId);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
