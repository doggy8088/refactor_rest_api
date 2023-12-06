package com.example.demo;

import com.example.demo.models.Product;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProductTest {

    protected Product samsungGalaxy;
    protected Product iPhone6S;
    protected List<Product> products;

    @BeforeEach
    public void setup() {
        samsungGalaxy = new Product("Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        iPhone6S = new Product("Apple iPhone 6S", "Newest mobile product from Apple.", 1299.99, 15.99);
        products = new ArrayList<>();
        products.add(samsungGalaxy);
        products.add(iPhone6S);
    }
}
