package com.codewithmosh.store.orders;

import com.codewithmosh.store.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrderById(long orderId) {
        var user = authService.getCurrentUser();
        var order = orderRepository.getOrderForCustomer(user, orderId).orElseThrow(OrderNotFoundException::new);
        return orderMapper.toDto(order);
    }
}
