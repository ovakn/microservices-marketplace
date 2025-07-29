package org.example.orderService.mappers;

import org.example.orderService.DTOs.OrderItemRequest;
import org.example.orderService.DTOs.OrderItemResponse;
import org.example.orderService.entities.Order;
import org.example.orderService.entities.OrderItem;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemMapperTest {
    private final OrderItemMapper orderItemMapper = new OrderItemMapper();

    @Test
    void toDTO() {
        OrderItem orderItem = new OrderItem(1L, 1L, 3, new Order());
        OrderItemResponse waitedOrderItemResponse = new OrderItemResponse(1L, 3);
        OrderItemResponse orderItemResponse = orderItemMapper.toDTO(orderItem);
        assertEquals(waitedOrderItemResponse, orderItemResponse);
    }

    @Test
    void toDTOList() {
        List<OrderItem> orderItems = List.of(
                new OrderItem(1L, 1L, 2, new Order()),
                new OrderItem(2L, 2L, 3, new Order())
        );
        List<OrderItemResponse> waitedOrderItemsResponse = List.of(
                new OrderItemResponse(1L, 2),
                new OrderItemResponse(2L, 3)
        );
        List<OrderItemResponse> orderItemsResponse = orderItemMapper.toDTOList(orderItems);
        assertEquals(waitedOrderItemsResponse, orderItemsResponse);
    }

    @Test
    void toOrderItem() {
        OrderItemRequest orderItemRequest = new OrderItemRequest(1L, 3);
        OrderItem waitedOrderItem = new OrderItem(null, 1L, 3, null);
        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemRequest);
        assertEquals(waitedOrderItem, orderItem);
    }

    @Test
    void toOrderItemsList() {
        List<OrderItemRequest> orderItemsRequest = List.of(
                new OrderItemRequest(1L, 3),
                new OrderItemRequest(2L, 2)
        );
        List<OrderItem> waitedOrderItems = List.of(
                new OrderItem(null, 1L, 3, null),
                new OrderItem(null, 2L, 2, null)
        );
        List<OrderItem> orderItems = orderItemMapper.toOrderItemsList(orderItemsRequest);
        assertEquals(waitedOrderItems, orderItems);
    }
}