package org.example.orderService.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderService.DTOs.OrderRequest;
import org.example.orderService.DTOs.OrderResponse;
import org.example.orderService.entities.Order;
import org.example.orderService.entities.OrderItem;
import org.example.orderService.entities.enums.OrderStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class OrderMapper {
    OrderItemMapper orderItemMapper;

    public OrderResponse toDTO(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getCreatedAt(),
                order.getStatus(),
                orderItemMapper.toDTOList(order.getItems())
        );
    }

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order(
                null,
                orderRequest.getUserId(),
                LocalDateTime.now(),
                OrderStatus.CREATED,
                new ArrayList<>()
        );
        List<OrderItem> items = orderItemMapper.toOrderItemsList(orderRequest.getItems());
        items.forEach(order::addItem);
        return order;
    }
}