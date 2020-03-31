package com.example.demo;

import com.example.demo.models.Product;
import com.example.demo.models.ProductOptions;
import com.example.demo.repository.ProductOptionsRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.services.ProductOptionsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductOptionsServiceTest {

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    @Autowired
    private ProductsRepository productsReposititory;

    @Autowired
    private ProductOptionsService productOptionsService;

    @BeforeEach
    public void createRepositories() {
        Product product = new Product( "Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsReposititory.save(product);
        ProductOptions productOptions1 = new ProductOptions(product, "White","White Samsung Galaxy S7");
        productOptionsRepository.save(productOptions1);
    }

    @AfterEach
    public void deleteRepositories() {
        productOptionsRepository.deleteAll();
        productsReposititory.deleteAll();
    }

    @Test
    void getAllOptions(){
        UUID productId = productsReposititory.findAll().get(0).getId();
        List<ProductOptions> productOptionsList = productOptionsRepository.findByProductId(productId);
        assertEquals(1, productOptionsList.size());
    }

    @Test
    void saveProductOptions(){
        Product product = productsReposititory.findAll().get(0);
        ProductOptions productOptions = new ProductOptions(product, "Pink","Pink Samsung Galaxy S7");
        long count = productOptionsRepository.count();
        productOptionsRepository.save(productOptions);
        assertEquals((count+1), productOptionsRepository.count());
    }

    @Test
    void findProductOption(){
        List<Product> productList = productsReposititory.findAll();
        String productId = productList.get(0).getId().toString();
        List<ProductOptions>  productOptionsList = productOptionsRepository.findAll();
        String optionId = productOptionsList.get(0).getId().toString();
        ProductOptions productOption = productOptionsService.getProductOption(productId, optionId);
        assertEquals(optionId, productOption.getId().toString());
        assertEquals("White", productOption.getName());
        assertEquals("White Samsung Galaxy S7", productOption.getDescription());
    }

    @Test
    void updateProductOption() {
        List<Product> productList = productsReposititory.findAll();
        String productId = productList.get(0).getId().toString();
        List<ProductOptions>  productOptionsList = productOptionsRepository.findAll();
        if(productOptionsList.size()>0){
            String optionId = productOptionsList.get(0).getId().toString();
            ProductOptions productOption = productOptionsService.getProductOption(productId,optionId);
            if(productOption != null){
                productOptionsRepository.save(productOption);
                assertEquals(productOptionsList.size(),productOptionsRepository.count());
            }
        }
    }

    @Test
    void deleteOption() {
        String productId = productsReposititory.findAll().get(0).getId().toString();
        String optionId = productOptionsRepository.findAll().get(0).getId().toString();
        long count = productOptionsRepository.count();
        productOptionsService.deleteOption(productId, optionId);
        assertEquals((count-1), productOptionsRepository.count());
    }

}
