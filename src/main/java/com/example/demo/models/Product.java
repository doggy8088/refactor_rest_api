package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="PRODUCT")
@ApiModel(description = "All details about the Product")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message="cannot be empty.")
    @Size(min=2, message="should be greater than 1 character")
    @ApiModelProperty(notes = "Name should be greater than 1 character")
    private String name;

    private String description;

    @NotNull(message="cannot be empty")
    @PositiveOrZero(message="cannot be less than zero")
    @ApiModelProperty(notes = "Price cannot be less than zero")
    private Double price;

    @PositiveOrZero(message="cannot be less than zero")
    @ApiModelProperty(notes = "Delivery Price cannot be less than zero")
    private Double deliveryPrice;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductOptions> productOptions;

    public Product(UUID id, String name, String description, Double price, Double deliveryPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
    }

    public Product(String name, String description, Double price, Double deliveryPrice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
    }

    public Product(){
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public List<ProductOptions> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptions> productOptions) {
        this.productOptions = productOptions;
    }
}
