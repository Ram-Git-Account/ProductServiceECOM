package com.productservice.productservice.models;

import com.productservice.productservice.dtos.GenericProductDto;
import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Document(indexName = "products")
public class Product extends BaseModel {
    private String title;
    private String description;
    private String image;

    //Category isn't a primitive attribute, it's a relation.
    //category_id in the product table.
    @ManyToOne(optional = false)
    private Category category;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Price price;
    private int inventoryCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }
}

/*
    1          1
 Product --- Category
     M         1
 */
