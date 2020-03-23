package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "PRODUCT_OPTIONS")
public class ProductOptions {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @NotNull(message="cannot be empty.")
    @Size(min=2, message="should be greater than 1 character")
    @ApiModelProperty(notes = "Name should be greater than 1 character")
    private String name;

    private String description;

    public ProductOptions(Product product, String name, String description) {
        this.product = product;
        this.name = name;
        this.description = description;
    }

    public ProductOptions(UUID id, Product product, String name, String description) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.description = description;
    }

    public ProductOptions() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

}
