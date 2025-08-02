package org.example.orderService.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.orderService.DTOs.*;
import org.example.orderService.entities.*;
import org.example.orderService.entities.enums.OrderStatus;
import org.example.orderService.feign.ProductServiceClient;
import org.example.orderService.mappers.OrderMapper;
import org.example.orderService.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class OrderService {
    OrderRepository orderRepository;
    ProductServiceClient productServiceClient;
    OrderMapper orderMapper;

    public OrderResponse getOrderById(Long id) {
        log.info("getting order with id: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order with such id does not exist")
        );
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("saving new order: " + orderRequest.toString());
        reservingProducts(orderRequest.getItems());
        Order order = orderMapper.toOrder(orderRequest);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse payOrder(Long id) {
        log.info("paying for the order wth id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order with such id does not exist")
        );
        order.setStatus(OrderStatus.PAID);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse shipOrder(Long id) {
        log.info("shipping the order wth id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order with such id does not exist")
        );
        order.setStatus(OrderStatus.SHIPPED);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse deliverOrder(Long id) {
        log.info("delivering the order wth id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order with such id does not exist")
        );
        order.setStatus(OrderStatus.DELIVERED);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long id) {
        log.info("cancelling the order wth id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order with such id does not exist")
        );
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toDTO(order);
    }

    private void reservingProducts(List<OrderItemRequest> itemsRequest) {
        List<ProductQuantityRequest> productsList = itemsRequest
                .stream()
                .map(item -> new ProductQuantityRequest(item.getProductId(), item.getQuantity())).toList();
        if (!productServiceClient.isProductAvailable(productsList)) {
            throw new IllegalArgumentException("Error. Some of products isn't available");
        }
        if (!productServiceClient.reserveProduct(productsList)) {
            throw new IllegalArgumentException("Error. Some of products isn't available");
        }
    }
}