package org.example.orderService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderService.DTOs.*;
import org.example.orderService.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Validated
@Tag(
        name = "Контроллер заказов",
        description = "Контроллер для взаимодействия с базой данных заказов"
)
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class OrderController {
    OrderService orderService;

    @Operation(
            summary = "Получение заказа",
            description = "Возвращает заказ по его id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "Идентификатор заказа", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание заказа",
            description = "Создаёт новый заказ и возвращает его"
    )
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@Validated @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Оплата заказа",
            description = "Меняет статус заказа в базе данных"
    )
    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> payOrder(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        return new ResponseEntity<>(orderService.payOrder(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Отправка заказа",
            description = "Меняет статус заказа в базе данных"
    )
    @PutMapping("/{id}/ship")
    public ResponseEntity<OrderResponse> shipOrder(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        return new ResponseEntity<>(orderService.shipOrder(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Доставка заказа",
            description = "Меняет статус заказа в базе данных"
    )
    @PutMapping("/{id}/deliver")
    public ResponseEntity<OrderResponse> deliverOrder(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        return new ResponseEntity<>(orderService.deliverOrder(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Отмена заказа",
            description = "Меняет статус заказа в базе данных"
    )
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        return new ResponseEntity<>(orderService.cancelOrder(id), HttpStatus.OK);
    }
}