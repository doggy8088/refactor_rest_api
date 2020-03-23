package com.example.demo.util;

import com.example.demo.models.ProductOptions;

import java.util.List;

/**
 * This class is invoked from the controller. This wraps the get_all_Product_Options response.
 */
public class ProductOptionsResponse {
    private List<ProductOptions> items;

    public List<ProductOptions> getItems() {
        return items;
    }

    public void setItems(List<ProductOptions> items) {
        this.items = items;
    }
}
