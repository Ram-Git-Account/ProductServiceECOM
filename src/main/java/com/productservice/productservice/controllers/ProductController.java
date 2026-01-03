package com.productservice.productservice.controllers;

import com.productservice.productservice.dtos.GenericProductDto;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import com.productservice.productservice.services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 🔹 GET PRODUCT BY ID
    @GetMapping("/{id}")
    public GenericProductDto getProductById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @PathVariable Long id
    ) throws ProductNotFoundException {

        return productService.getProductById(authToken, id);
    }

    // 🔹 GET ALL PRODUCTS
    @GetMapping
    public List<GenericProductDto> getAllProducts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken
    ) {
        return productService.getAllProducts(authToken);
    }


    // 🔹 CREATE PRODUCT
    @PostMapping
    public GenericProductDto createProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @RequestBody GenericProductDto dto
    ) {
        return productService.createProduct(dto);
    }

    // 🔹 DELETE PRODUCT
    @DeleteMapping("/{id}")
    public GenericProductDto deleteProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @PathVariable Long id
    ) {
        return productService.deleteProductById(id);
    }
}
