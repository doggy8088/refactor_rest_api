package com.example.demo.models;

import static java.util.UUID.randomUUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "PRODUCT")
@ApiModel(description = "All details about the Product")
public class Product {

    @Id
    private String id;

    @NotNull(message = "cannot be empty.")
    @Size(min = 2, message = "should be greater than 1 character")
    @ApiModelProperty(notes = "Name should be greater than 1 character")
    private String name;

    private String description;

    @NotNull(message = "cannot be empty")
    @PositiveOrZero(message = "cannot be less than zero")
    @ApiModelProperty(notes = "Price cannot be less than zero")
    private Double price;

    @PositiveOrZero(message = "cannot be less than zero")
    @ApiModelProperty(notes = "Delivery Price cannot be less than zero")
    private Double deliveryPrice;

    public Product(String name, String description, Double price, Double deliveryPrice) {
        this.id = randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
