package com.productservice.productservice.dtos;

import com.productservice.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateOrderDto {

    // Authenticated user ID (from token or gateway)
    private Long userId;

    // Product IDs user wants to buy
    private List<UUID> productIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<UUID> productIds) {
        this.productIds = productIds;
    }
}
