package com.productservice.productservice.services;

import com.productservice.productservice.ProductServiceApplication;
import com.productservice.productservice.dtos.GenericProductDto;
import com.productservice.productservice.dtos.UserDto;
import com.productservice.productservice.dtos.ValidateTokenRequestDto;
import com.productservice.productservice.exceptions.ProductNotFoundException;
import com.productservice.productservice.models.Product;
//import com.productservice.productservice.repositories.OpenSearchProductRepository;
import com.productservice.productservice.models.SessionStatus;
import com.productservice.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Service
public class SelfProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private RestTemplate restTemplate;
    //private OpenSearchProductRepository openSearchProductRepository;

    SelfProductServiceImpl(ProductRepository productRepository, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
        //this.openSearchProductRepository = openSearchProductRepository;
    }

    @Override
    public GenericProductDto getProductById(String authToken, Long id)
            throws ProductNotFoundException {

        // 🔥 LOG IT (DO THIS ONCE)
        System.out.println("Incoming token = [" + authToken + "]");

        // 1️⃣ Normalize token
        String token = authToken.replace("Bearer ", "").trim();

        // 2️⃣ Call UserService ONLY to validate token
        ValidateTokenRequestDto validateRequest = new ValidateTokenRequestDto();
        validateRequest.setToken(token);
       // validateRequest.setUserId(null); // ❗ userId should NOT come from product service

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ValidateTokenRequestDto> entity =
                new HttpEntity<>(validateRequest, headers);

        ResponseEntity<SessionStatus> response =
                restTemplate.exchange(
                        "http://localhost:9090/api/users/validate",
                        HttpMethod.POST,   // 🔴 SHOULD NOT BE GET with body
                        entity,
                        SessionStatus.class
                );


        if (response.getBody() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Invalid token");
        }

        // 3️⃣ Token valid → fetch product (dummy example)
        GenericProductDto product = new GenericProductDto();
        product.setId(id);
        product.setTitle("Product " + id);
        product.setDescription("Fetched after token validation");
        product.setCategory("USER_PRODUCT");

        return product;
    }


    public List<GenericProductDto> getAllProducts(String authHeader) {

        // 1️⃣ Extract token
        String token = authHeader.replace("Bearer ", "").trim();

        // 2️⃣ Build validate request
        ValidateTokenRequestDto validateRequest = new ValidateTokenRequestDto();
        validateRequest.setToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ValidateTokenRequestDto> entity =
                new HttpEntity<>(validateRequest, headers);

        // 3️⃣ Call UserService
        ResponseEntity<SessionStatus> response =
                restTemplate.exchange(
                        "http://localhost:9090/api/users/validate",
                        HttpMethod.POST,
                        entity,
                        SessionStatus.class
                );

        if (response.getBody() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Unauthorized");
        }

        // 4️⃣ Token valid → return products (dummy data for now)
        List<GenericProductDto> products = new ArrayList<>();

        products.add(new GenericProductDto("Mobile",1000000, "iPhone", "Apple phone", "MOBILE", 1));
        products.add(new GenericProductDto("GAdget",2000000, "MacBook", "Apple laptop", "LAPTOP", 1));
        products.add(new GenericProductDto("Accesories",350000, "AirPods", "Apple earbuds", "ACCESSORIES", 5));

        return products;
    }


    @Override
    public GenericProductDto deleteProductById(Long id) {
        return null;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
//        Product product = new Product();
//        product.setTitle(genericProductDto.getTitle());
//        product.setImage(genericProductDto.getImage());
//        //
//
//        Product savedProduct  = productRepository.save(product);
        //openSearchProductRepository.save(savedProduct);
        return null;
    }

    @Override
    public void updateProductById() {

    }
}
