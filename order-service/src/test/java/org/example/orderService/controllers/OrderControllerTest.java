package org.example.orderService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.orderService.DTOs.OrderItemRequest;
import org.example.orderService.DTOs.OrderItemResponse;
import org.example.orderService.DTOs.OrderRequest;
import org.example.orderService.DTOs.OrderResponse;
import org.example.orderService.entities.enums.OrderStatus;
import org.example.orderService.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockitoBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getOrderById() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.CREATED,
                List.of(new OrderItemResponse(15L, 2))
        );
        when(orderService.getOrderById(1L)).thenReturn(response);
        mockMvc
                .perform(get("/api/v1/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.items[0].productId").value(15L))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void getOrderById_returnsNotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(get("/api/v1/orders/{id}", 1L)).andExpect(status().isNotFound());
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void createOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest(1L, List.of(new OrderItemRequest(12L, 2)));
        String jsonOrder = objectMapper.writeValueAsString(orderRequest);
        OrderResponse orderResponse = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.CREATED,
                List.of(new OrderItemResponse(12L, 2))
        );
        when(orderService.createOrder(orderRequest)).thenReturn(orderResponse);
        mockMvc
                .perform(post("/api/v1/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.items[0].productId").value(12L))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        verify(orderService, times(1)).createOrder(any());
    }

    @Test
    void createOrder_returnsBadRequest() throws Exception {
        OrderRequest orderRequest = new OrderRequest(0L, null);
        String jsonOrder = objectMapper.writeValueAsString(orderRequest);
        mockMvc
                .perform(post("/api/v1/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(status().isBadRequest());
        verify(orderService, times(0)).createOrder(any());
    }

    @Test
    void payOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.PAID,
                List.of(new OrderItemResponse(15L, 2))
        );
        when(orderService.payOrder(1L)).thenReturn(response);
        mockMvc
                .perform(put("/api/v1/orders/{id}/pay", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.items[0].productId").value(15L))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        verify(orderService, times(1)).payOrder(1L);
    }

    @Test
    void shipOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.SHIPPED,
                List.of(new OrderItemResponse(15L, 2))
        );
        when(orderService.shipOrder(1L)).thenReturn(response);
        mockMvc
                .perform(put("/api/v1/orders/{id}/ship", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.status").value("SHIPPED"))
                .andExpect(jsonPath("$.items[0].productId").value(15L))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        verify(orderService, times(1)).shipOrder(1L);
    }

    @Test
    void deliverOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.DELIVERED,
                List.of(new OrderItemResponse(15L, 2))
        );
        when(orderService.deliverOrder(1L)).thenReturn(response);
        mockMvc
                .perform(put("/api/v1/orders/{id}/deliver", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.status").value("DELIVERED"))
                .andExpect(jsonPath("$.items[0].productId").value(15L))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        verify(orderService, times(1)).deliverOrder(1L);
    }

    @Test
    void cancelOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                1L,
                null,
                OrderStatus.CANCELLED,
                List.of(new OrderItemResponse(15L, 2))
        );
        when(orderService.cancelOrder(1L)).thenReturn(response);
        mockMvc
                .perform(put("/api/v1/orders/{id}/cancel", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.status").value("CANCELLED"))
                .andExpect(jsonPath("$.items[0].productId").value(15L))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
        verify(orderService, times(1)).cancelOrder(1L);
    }
}