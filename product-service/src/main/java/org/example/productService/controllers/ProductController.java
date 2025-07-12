package org.example.productService.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productService.DTOs.ProductDTO;
import org.example.productService.entities.Product;
import org.example.productService.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll() {
//        try {
//            return ResponseEntity.ok(productService.getAllProducts());
//        } catch (Exception e) {
//            return ResponseEntity.noContent().build();
//        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody ProductDTO product) {
        try {
            return ResponseEntity.ok(productService.createProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
    }
}