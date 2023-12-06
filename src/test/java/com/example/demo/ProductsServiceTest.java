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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductsServiceTest extends AbstractProductTest {

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ProductsService productsService;

    @BeforeEach
    public void createProductRepository() {
        productsRepository.save(samsungGalaxy);
        productsRepository.save(iPhone6S);
    }

    @AfterEach
    public void deleteProductRepository() {
        productsRepository.deleteAll();
    }

    @Test
    void getAllProducts() {
        List<Product> products = productsService.findAll();
        assertEquals(2, products.size());
    }

    @Test
    void saveProduct() {
        // given
        Product product = new Product("HauWei", "Newest mobile product from HauWei.", 104.99, 16.99);
        long count = productsRepository.count();
        // when
        productsService.save(product);
        // then
        assertEquals((count + 1), productsRepository.count());
    }

    @Test
    void findProductByName() {
        List<Product> products = productsService.find(samsungGalaxy.getName());

        assertEquals(1, products.size());
        Product product = products.get(0);
        assertEquals(samsungGalaxy.getName(), product.getName());
        assertEquals(samsungGalaxy.getDescription(), product.getDescription());
        assertEquals(samsungGalaxy.getPrice(), product.getPrice());
        assertEquals(samsungGalaxy.getDeliveryPrice(), product.getDeliveryPrice());

    }

    @Test
    void findProductById() {
        List<Product> products = productsService.findAll();

        products.forEach(product -> {
            Product productById = productsService.findById(product.getId());
            assertEquals(product.getName(), productById.getName());
            assertEquals(product.getDescription(), productById.getDescription());
            assertEquals(product.getPrice(), productById.getPrice());
            assertEquals(product.getDeliveryPrice(), productById.getDeliveryPrice());
        });
    }

    @Test
    void updateProductById() {
        samsungGalaxy.setPrice(1000.00);
        samsungGalaxy.setDeliveryPrice(10.00);

        productsService.update(samsungGalaxy);

        Product product = productsService.findById(samsungGalaxy.getId());
        assertEquals(samsungGalaxy.getPrice(), product.getPrice());
        assertEquals(samsungGalaxy.getDescription(), product.getDescription());
    }

    @Test
    void deleteProduct() {
        long currentCount = productsRepository.count();
        productsRepository.deleteById(samsungGalaxy.getId());
        assertEquals((currentCount - 1), productsRepository.count());
    }
}
