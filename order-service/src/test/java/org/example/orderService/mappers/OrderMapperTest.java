package org.example.orderService.mappers;

import org.example.orderService.DTOs.OrderItemRequest;
import org.example.orderService.DTOs.OrderItemResponse;
import org.example.orderService.DTOs.OrderRequest;
import org.example.orderService.DTOs.OrderResponse;
import org.example.orderService.entities.Order;
import org.example.orderService.entities.OrderItem;
import org.example.orderService.entities.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {
    @Mock
    private OrderItemMapper orderItemMapper;
    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    void toDTO() {
        Order order = new Order(
                1L,
                1L,
                null,
                OrderStatus.CREATED,
                new ArrayList<>()
        );
        order.addItem(new OrderItem(1L, 1L, 3, null));
        OrderResponse waitedOrderResponse = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.CREATED,
                List.of(new OrderItemResponse(1L, 3))
        );
        when(orderItemMapper.toDTOList(any())).thenReturn(List.of(new OrderItemResponse(1L, 3)));
        OrderResponse orderResponse = orderMapper.toDTO(order);
        assertEquals(waitedOrderResponse, orderResponse);
        verify(orderItemMapper, times(1)).toDTOList(any());
    }

    @Test
    void toOrder() {
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemRequest(1L, 2)));
        Order waitedOrder = new Order(null, 1L, null, OrderStatus.CREATED, new ArrayList<>());
        OrderItem expectedItem = new OrderItem(null, 1L, 2, waitedOrder);
        waitedOrder.addItem(expectedItem);
        when(orderItemMapper.toOrderItemsList(any())).thenReturn(List.of(expectedItem));
        Order order = orderMapper.toOrder(orderRequest);
        assertEquals(waitedOrder.getId(), order.getId());
        assertEquals(waitedOrder.getUserId(), order.getUserId());
        assertEquals(waitedOrder.getStatus(), order.getStatus());
        assertEquals(waitedOrder.getItems(), order.getItems());
        verify(orderItemMapper, times(1)).toOrderItemsList(any());
    }
}