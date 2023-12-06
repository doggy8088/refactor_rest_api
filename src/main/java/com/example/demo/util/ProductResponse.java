package com.example.demo.util;

import java.util.List;

import com.example.demo.models.Product;

/**
 * This class is invoked from the controller. This wraps the get_all_Products
 * response.
 */
public class ProductResponse {

    private List<Product> items;

    public ProductResponse(List<Product> items) {
        this.items = items;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
