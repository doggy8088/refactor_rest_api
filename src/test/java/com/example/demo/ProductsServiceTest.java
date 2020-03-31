package com.example.demo;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.services.ProductsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductsServiceTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsService productsService;

    @BeforeEach
    public void createProductRepository(){
        Product product1 = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        Product product2 = new Product( "Apple iPhone 6S", "Newest mobile product from Apple.", 1224.99, 17.99);
        productsRepository.save(product1);
        productsRepository.save(product2);
    }

    @AfterEach
    public void deleteProductRepository(){
        productsRepository.deleteAll();
    }

    @Test
    void getAllProducts(){
        List<Product> productsList = productsService.findAll();
        assertEquals(2, productsList.size());
    }

    @Test
    void saveProduct() {
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        long count = productsRepository.count();
        productsService.save(product);
        assertEquals((count+1), productsRepository.count());
    }

    @Test
    void findProductByName() {
        String name = "Samsung Galaxy S7";
        List<Product> productList = productsService.find(name);
        assertEquals(1.0, productList.size());
        assertEquals(productList.get(0).getName(), name);
        assertEquals(productList.get(0).getDescription(), "Newest mobile product from Samsung.");
        assertEquals(productList.get(0).getPrice(), 1024.99);
        assertEquals(productList.get(0).getDeliveryPrice(), 16.99);

    }

    @Test
    void findProductById() {
        List<Product> productList = productsService.findAll();
        if(productList.size()>0){
            UUID uuid = productList.get(0).getId();
            Optional<Product> product = productsService.findById(uuid);
            assertTrue(product.isPresent());
        }
    }

    @Test
    void updateProductById() {
        List<Product> productList = productsService.findAll();
        if(productList.size()>0){
            UUID uuid = productList.get(0).getId();
            Optional<Product> product = productsService.findById(uuid);
            if(product.isPresent()){
                productsRepository.save(product.get());
                assertEquals(productList.size(),productsRepository.count());
            }
        }
    }

    @Test
    void deleteProduct() {
        UUID uuid = productsRepository.findAll().get(0).getId();
        long currentCount = productsRepository.count();
        productsRepository.deleteById(uuid);
        assertEquals((currentCount-1), productsRepository.count());
    }
}
