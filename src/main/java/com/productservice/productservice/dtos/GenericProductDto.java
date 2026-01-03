package com.productservice.productservice.dtos;

import com.productservice.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GenericProductDto {
    private Long id;
    private String title;
    private int price;
    private String category;
    private String description;
    private String image;
    private int inventoryCount;

    public GenericProductDto(String title, int price, String category, String description, String image, int inventoryCount) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.inventoryCount = inventoryCount;
    }
    public GenericProductDto() {
    }

    public static GenericProductDto from(Product product) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setDescription(product.getDescription());
        //genericProductDto.setPrice(product.getPrice());
        genericProductDto.setImage(product.getImage());
        //genericProductDto.setId(product.getId());
        genericProductDto.setInventoryCount(product.getInventoryCount());
        return genericProductDto;
    }
}
