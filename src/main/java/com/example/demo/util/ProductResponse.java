package com.example.demo.util;

import com.example.demo.models.Product;

import java.util.List;

/**
 * This class is invoked from the controller. This wraps the get_all_Products response.
 */
public class ProductResponse {

    private List<Product> items;

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
