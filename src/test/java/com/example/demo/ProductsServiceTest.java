package com.example.demo;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.services.ProductsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductsServiceTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    void getAllProducts(){
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsRepository.save(product);
        ProductsService productsService = new ProductsService(productsRepository);
        assertEquals(1.0, productsRepository.count());

        List<Product> productsList = productsService.findAll();
        Product lastProduct = productsList.get(0);
        assertEquals(lastProduct.getName(), product.getName());
        assertEquals(lastProduct.getDescription(), product.getDescription());
        assertEquals(lastProduct.getPrice(), product.getPrice());
        assertEquals(lastProduct.getDeliveryPrice(), product.getDeliveryPrice());
    }

    @Test
    void saveProduct() {
        Product product = new Product( "Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        ProductsService productsService = new ProductsService(productsRepository);
        long count = productsRepository.count();
        productsService.save(product);
        assertEquals((count+1), productsRepository.count());
    }

    @Test
    void findProductByName() {
        String name = "Samsung Galaxy S7";
        ProductsService productsService = new ProductsService(productsRepository);
        List<Product> productList = productsService.find(name);
        assertEquals(1.0, productList.size());
        assertEquals(productList.get(0).getName(), name);
    }

    @Test
    void findProductById() {
        ProductsService productsService = new ProductsService(productsRepository);
        List<Product> productList = productsService.findAll();
        if(productList.size()>0){
            UUID uuid = productList.get(0).getId();
            Optional<Product> product = productsService.findById(uuid);
            assertEquals(true, product.isPresent());
        }
    }

    @Test
    void updateProductById() {
        ProductsService productsService = new ProductsService(productsRepository);
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
        ProductsService productsService = new ProductsService(productsRepository);
        productsRepository.deleteAll();
        Product product = new Product("Samsung Galaxy S7", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsRepository.save(product);
        UUID uuid = productsRepository.findAll().get(0).getId();
        long currentCount = productsRepository.count();
        productsRepository.deleteById(uuid);
        assertEquals((currentCount-1), productsRepository.count());

    }
}
