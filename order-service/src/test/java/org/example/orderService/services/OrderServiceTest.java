package org.example.orderService.services;

import org.example.orderService.DTOs.OrderItemRequest;
import org.example.orderService.DTOs.OrderRequest;
import org.example.orderService.entities.Order;
import org.example.orderService.entities.OrderItem;
import org.example.orderService.entities.enums.OrderStatus;
import org.example.orderService.exceptions.OrderNotExistsException;
import org.example.orderService.exceptions.UserNotExistsException;
import org.example.orderService.feign.ProductServiceClient;
import org.example.orderService.feign.UserServiceClient;
import org.example.orderService.mappers.OrderMapper;
import org.example.orderService.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductServiceClient productServiceClient;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private OrderMapper orderMapper;

    @Test
    void getOrderById() {
        Order order = new Order(1L, 130L, LocalDateTime.now(), OrderStatus.PAID, new ArrayList<>());
        order.setItems(List.of(
                new OrderItem(1L, 42L, 8, order),
                new OrderItem(2L, 40L, 1, order)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertEquals(orderMapper.toDTO(order), orderService.getOrderById(1L));
        verify(orderRepository, times(1)).findById(any());
    }

    @Test
    void getOrderById_throwsException() {
        Order order = new Order(2L, 128L, LocalDateTime.now(), OrderStatus.CREATED, new ArrayList<>());
        order.setItems(List.of(
                new OrderItem(3L, 92L, 2, order),
                new OrderItem(4L, 87L, 3, order)
        ));
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        assertThrows(OrderNotExistsException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void createOrder() {
        OrderRequest order = new OrderRequest(
                194L,
                List.of(
                        new OrderItemRequest(12L, 3)
                )
        );
        when(productServiceClient.isProductAvailable(any())).thenReturn(true);
        when(productServiceClient.reserveProduct(any())).thenReturn(true);
        when(userServiceClient.doesExistById(any())).thenReturn(true);
        assertEquals(orderMapper.toDTO(orderMapper.toOrder(order)), orderService.createOrder(order));
    }

    @Test
    void createOrder_throwsException() {
        OrderRequest order = new OrderRequest(
                194L,
                List.of(
                        new OrderItemRequest(12L, 3)
                )
        );
        when(productServiceClient.isProductAvailable(any())).thenReturn(false);
        assertThrows(UserNotExistsException.class, () -> orderService.createOrder(order));
    }

    @Test
    void payOrder() {
        Order order = new Order(1L, 130L, null, OrderStatus.CREATED, new ArrayList<>());
        order.setItems(List.of(
                new OrderItem(1L, 42L, 8, order),
                new OrderItem(2L, 40L, 1, order)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order order2 = new Order(1L, 130L, null, OrderStatus.PAID, new ArrayList<>());
        order2.setItems(List.of(
                new OrderItem(1L, 42L, 8, order2),
                new OrderItem(2L, 40L, 1, order2)
        ));
        assertEquals(orderMapper.toDTO(order2), orderService.payOrder(1L));
    }

    @Test
    void shipOrder() {
        Order order = new Order(1L, 130L, null, OrderStatus.CREATED, new ArrayList<>());
        order.setItems(List.of(
                new OrderItem(1L, 42L, 8, order),
                new OrderItem(2L, 40L, 1, order)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order order2 = new Order(1L, 130L, null, OrderStatus.SHIPPED, new ArrayList<>());
        order2.setItems(List.of(
                new OrderItem(1L, 42L, 8, order2),
                new OrderItem(2L, 40L, 1, order2)
        ));
        assertEquals(orderMapper.toDTO(order2), orderService.shipOrder(1L));
    }

    @Test
    void deliverOrder() {
        Order order = new Order(1L, 130L, null, OrderStatus.CREATED, new ArrayList<>());
        order.setItems(List.of(
                new OrderItem(1L, 42L, 8, order),
                new OrderItem(2L, 40L, 1, order)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order order2 = new Order(1L, 130L, null, OrderStatus.DELIVERED, new ArrayList<>());
        order2.setItems(List.of(
                new OrderItem(1L, 42L, 8, order2),
                new OrderItem(2L, 40L, 1, order2)
        ));
        assertEquals(orderMapper.toDTO(order2), orderService.deliverOrder(1L));
    }

    @Test
    void cancelOrder() {
        Order order = new Order(1L, 130L, null, OrderStatus.CREATED, new ArrayList<>());
        order.setItems(List.of(
                new OrderItem(1L, 42L, 8, order),
                new OrderItem(2L, 40L, 1, order)
        ));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order order2 = new Order(1L, 130L, null, OrderStatus.CANCELLED, new ArrayList<>());
        order2.setItems(List.of(
                new OrderItem(1L, 42L, 8, order2),
                new OrderItem(2L, 40L, 1, order2)
        ));
        assertEquals(orderMapper.toDTO(order2), orderService.cancelOrder(1L));
    }
}