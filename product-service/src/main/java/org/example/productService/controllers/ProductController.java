package org.example.productService.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productService.DTOs.ProductDTO;
import org.example.productService.entities.Product;
import org.example.productService.services.ProductService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping("/getSlice")
    public ResponseEntity<Slice<Product>> getSlice(
            @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(50) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset
    ) {
        try {
            return ResponseEntity.ok(productService.getProductsSlice(limit, offset));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") @Min(0) Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(productService.getProductByName(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@Validated @RequestBody ProductDTO product) {
        try {
            return ResponseEntity.ok(productService.createProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(
            @Validated @RequestBody ProductDTO product,
            @PathVariable("id") @Min(0) Long id
    ) {
        try {
            return ResponseEntity.ok(productService.updateProduct(product, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") @Min(0) Long id) {
        productService.deleteProduct(id);
    }
}