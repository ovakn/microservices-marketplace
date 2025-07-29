package org.example.orderService.DTOs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.orderService.entities.enums.OrderStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse implements Serializable {
    Long id;
    Long userId;
    LocalDateTime createdAt;
    OrderStatus status;
    List<OrderItemResponse> items;
}