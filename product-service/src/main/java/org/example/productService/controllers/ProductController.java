package org.example.productService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productService.DTOs.*;
import org.example.productService.services.ProductService;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@Validated
@Tag(
        name = "Контроллер продуктов",
        description = "Контроллер для взаимодействия с базой данных продуктов"
)
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class ProductController {
    ProductService productService;

    @Operation(
            summary = "Получение списка продуктов",
            description = "Возвращает страницу продуктов заданного размера"
    )
    @GetMapping("/getSlice")
    public ResponseEntity<Slice<ProductResponse>> getProductSlice(
            @Parameter(description = "Количество элементов на одной странице")
            @RequestParam(value = "limit", defaultValue = "20")
            @Min(1) @Max(100)
            Integer limit,
            @Parameter(description = "Количество пройденных страниц")
            @RequestParam(value = "offset", defaultValue = "0")
            @Min(0)
            Integer offset
    ) {
        return new ResponseEntity<>(productService.getProductsSlice(limit, offset), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение отзывов к продукту",
            description = "Возвращает страницу отзывов к продукту заданного размера"
    )
    @GetMapping("/{id}/reviews")
    public ResponseEntity<Slice<ReviewResponse>> getProductReviewsSlice(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id,
            @Parameter(description = "Количество элементов на одной странице")
            @RequestParam(value = "limit", defaultValue = "20")
            @Min(1) @Max(100)
            Integer limit,
            @Parameter(description = "Количество пройденных страниц")
            @RequestParam(value = "offset", defaultValue = "0")
            @Min(0)
            Integer offset
    ) {
        return new ResponseEntity<>(productService.getProductReviewsSlice(id, limit, offset), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение продукта",
            description = "Возвращает продукт по его id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(1)
            Long id
    ) {
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Получение продукта",
            description = "Возвращает продукт по его названию"
    )
    @GetMapping("/getByName/{name}")
    public ResponseEntity<ProductResponse> getProductByName(
            @Parameter(description = "Название продукта", required = true) @PathVariable("name") String name
    ) {
        try {
            return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Проверка наличия продукта",
            description = "Проверяет наличие на складе"
    )
    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> isAvailable(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable("id") @Min(1) Long id,
            @Parameter(description = "Количество продуктов", required = true) @RequestParam @Min(1) int quantity
    ) {
        try {
            return new ResponseEntity<>(productService.getAvailability(id, quantity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Добавление продукта",
            description = "Добавляет в базу данных новый продукт"
    )
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Объект создаваемого продукта", required = true)
            @Validated
            @RequestBody
            ProductRequest product
    ) {
        try {
            return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Добавление отзыва",
            description = "Добавляет в базу данных отзыв к конкретному продукту"
    )
    @PostMapping("/{productId}/createReview")
    public ResponseEntity<ReviewResponse> createReview(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("productId")
            @Min(1)
            Long productId,
            @Validated
            @RequestBody
            ReviewRequest reviewRequest
    ) {
        try {
            return new ResponseEntity<>(productService.createReview(productId, reviewRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Обновление продукта",
            description = "Обновляет информацию о продукте в базе данных"
    )
    @PutMapping("/{id}/update")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Идентификатор обновляемого продукта", required = true)
            @PathVariable(name = "id")
            @Min(1)
            Long id,
            @Parameter(description = "Объект с обновлёнными данными о продукте", required = true)
            @Validated
            @RequestBody
            ProductRequest product
    ) {
        try {
            return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Резервирование продукта",
            description = "Уменьшает количество товара на складе на переданное количество"
    )
    @PutMapping("/{id}/reserve")
    public ResponseEntity<Void> reserveProduct(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable(name = "id") @Min(1) Long id,
            @Parameter @RequestParam @Min(1) int quantity
    ) {
        try {
            productService.reserveProduct(id, quantity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Удаление продукта",
            description = "Удаляет продукт по id"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Идентификатор удаляемого продукта", required = true)
            @PathVariable(name = "id")
            @Min(1)
            Long id
    ) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}