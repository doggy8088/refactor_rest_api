package com.example.demo;

import com.example.demo.models.Product;
import com.example.demo.models.ProductOptions;
import com.example.demo.repository.ProductOptionsRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.services.ProductOptionsService;
import com.example.demo.services.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductOptionsServiceTest {

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    @Autowired
    private ProductsRepository productsReposititory;

    @Test
    void getAllOptions(){
        //String id = "f93975b1-7fc4-4652-8808-236b8f071602";
        productOptionsRepository.deleteAll();
        productsReposititory.deleteAll();

        Product product = new Product( "Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsReposititory.save(product);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        productOptionsRepository.save(productOptions);

        ProductOptionsService productOptionsService = new ProductOptionsService(productsReposititory, productOptionsRepository);
        UUID productId = productsReposititory.findAll().get(0).getId();
        List<ProductOptions> productOptionsList = productOptionsRepository.findByProductId(productId);
        assertEquals(productOptions.getName(), productOptionsList.get(0).getName());
        assertEquals(productOptions.getDescription(), productOptionsList.get(0).getDescription());
    }

    @Test
    void saveProductOptions(){
        Product product = new Product("Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsReposititory.save(product);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        long count = productOptionsRepository.count();
        productOptionsRepository.save(productOptions);
        assertEquals((count+1), productOptionsRepository.count());
    }

    @Test
    void findProductOption(){
        Product product = new Product("Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsReposititory.save(product);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        productOptionsRepository.save(productOptions);
        List<ProductOptions>  productOptionsList = productOptionsRepository.findAll();
        String optionId = productOptionsList.get(0).getId().toString();
        ProductOptionsService productsService = new ProductOptionsService(productsReposititory, productOptionsRepository);
        ProductOptions productOption = productsService.getProductOption(product.getId().toString(), optionId);
        assertEquals(optionId, productOption.getId().toString());
        assertEquals("White", productOption.getName());
        assertEquals("White Samsung Galaxy S7", productOption.getDescription());
    }

    @Test
    void updateProductOption() {
        Product product = new Product("Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsReposititory.save(product);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        productOptionsRepository.save(productOptions);
        List<ProductOptions>  productOptionsList = productOptionsRepository.findAll();
        ProductOptionsService productOptionsService = new ProductOptionsService(productsReposititory, productOptionsRepository);
        if(productOptionsList.size()>0){
            String optionId = productOptionsList.get(0).getId().toString();
            ProductOptions productOption = productOptionsService.getProductOption(product.getId().toString(),optionId);
            if(productOption != null){
                productOptionsRepository.save(productOption);
                assertEquals(productOptionsList.size(),productOptionsRepository.count());
            }
        }
    }

    @Test
    void deleteOption() {
        productOptionsRepository.deleteAll();
        productsReposititory.deleteAll();
        Product product = new Product("Samsung Galaxy 10", "Newest mobile product from Samsung.", 1024.99, 16.99);
        productsReposititory.save(product);
        ProductOptions productOptions = new ProductOptions(product, "White","White Samsung Galaxy S7");
        productOptionsRepository.save(productOptions);
        ProductOptionsService productOptionsService = new ProductOptionsService(productsReposititory, productOptionsRepository);
        String productId = productsReposititory.findAll().get(0).getId().toString();
        String optionId = productOptionsRepository.findAll().get(0).getId().toString();
        long count = productOptionsRepository.count();
        productOptionsService.deleteOption(productId, optionId);
        assertEquals((count-1), productOptionsRepository.count());
    }

}
