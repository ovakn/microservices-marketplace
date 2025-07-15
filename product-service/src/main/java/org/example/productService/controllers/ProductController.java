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
import org.example.productService.entities.*;
import org.example.productService.services.ProductService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
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
        try {
            return ResponseEntity.ok(productService.getProductsSlice(limit, offset));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Получение отзывов к продукту",
            description = "Возвращает страницу отзывов к продукту заданного размера"
    )
    @GetMapping("/{id}/reviews")
    public ResponseEntity<Slice<ReviewResponse>> getProductReviewsSlice(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(0)
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
        try {
            return ResponseEntity.ok(productService.getProductReviewsSlice(id, limit, offset));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Получение продукта",
            description = "Возвращает продукт по его id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable("id")
            @Min(0)
            Long id
    ) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            return ResponseEntity.ok(productService.getProductByName(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            return ResponseEntity.ok(productService.createProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            @Min(0)
            Long productId,
            @Validated
            @RequestBody
            ReviewRequest reviewRequest
    ) {
        try {
            return ResponseEntity.ok(productService.createReview(productId, reviewRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Обновление продукта",
            description = "Обновляет информацию о продукте в базе данных"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Идентификатор обновляемого продукта", required = true)
            @PathVariable("id")
            @Min(0)
            Long id,
            @Parameter(description = "Объект с обновлёнными данными о продукте", required = true)
            @Validated
            @RequestBody
            ProductRequest product
    ) {
        try {
            return ResponseEntity.ok(productService.updateProduct(product, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Удаление продукта",
            description = "Удаляет продукт по id"
    )
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(
            @Parameter(description = "Идентификатор удаляемого продукта", required = true)
            @PathVariable(name = "id")
            @Min(0)
            Long id
    ) {
        productService.deleteProduct(id);
    }
}