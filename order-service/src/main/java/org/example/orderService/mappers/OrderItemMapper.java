package org.example.orderService.mappers;

import org.example.orderService.DTOs.OrderItemRequest;
import org.example.orderService.DTOs.OrderItemResponse;
import org.example.orderService.entities.OrderItem;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemMapper {
    public OrderItemResponse toDTO(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getProductId(),
                orderItem.getQuantity()
        );
    }

    public List<OrderItemResponse> toDTOList(List<OrderItem> items) {
        return new ArrayList<>(
                items.stream().map(this::toDTO).toList()
        );
    }

    public OrderItem toOrderItem(OrderItemRequest orderItemRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemRequest.getQuantity());
        orderItem.setProductId(orderItemRequest.getProductId());
        return orderItem;
    }

    public List<OrderItem> toOrderItemsList(List<OrderItemRequest> orderItemsRequest) {
        return new ArrayList<>(
                orderItemsRequest.stream().map(this::toOrderItem).toList()
        );
    }
}