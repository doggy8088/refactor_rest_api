package com.example.demo.services;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.exceptions.ProductOptionsNotFoundException;
import com.example.demo.models.Product;
import com.example.demo.models.ProductOptions;
import com.example.demo.repository.ProductOptionsRepository;
import com.example.demo.repository.ProductsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This service class talks to the repositories for the data
 */
@Service
public class ProductOptionsService {

    private static final Logger logger = LogManager.getLogger(ProductOptionsService.class);

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Transactional
    public List<ProductOptions> getAllOptions(String productId){
        logger.info("ProductOptionsService : getAllOptions: productId = "+productId);
        Optional<Product> product = productsRepository.findById(UUID.fromString(productId));
        if(product.isPresent()){
            return productOptionsRepository.findByProductId(UUID.fromString(productId));
        } else {
            throw new ProductNotFoundException("Product Id "+productId+" not found");
        }
    }

    public ProductOptions save(String productId, ProductOptions productOptions) {
        logger.info("ProductOptionsService : save: productId = "+productId);
        Optional<Product> product = productsRepository.findById(UUID.fromString(productId));
        if(product.isPresent()){
            productOptions.setProduct(product.get());
            return productOptionsRepository.save(productOptions);
        } else
            throw new ProductNotFoundException("Product Id "+productId+" not found");
    }

    public ProductOptions getProductOption(String productId, String optionId){
        logger.info("ProductOptionsService : getProductOption: productId = "+productId);
        productsRepository.findById(UUID.fromString(productId)).orElseThrow(()
                -> new ProductNotFoundException("Product Id "+productId+" not found"));
        Optional<ProductOptions> productOptions = productOptionsRepository.findById(UUID.fromString(optionId));
        if(productOptions.isPresent()){
            return productOptions.get();
        } else
            throw new ProductOptionsNotFoundException("Product Option : "+optionId+" not found.");
    }

    public ProductOptions updateOption(String productId, String optionId, ProductOptions newProductOptions){
        logger.info("ProductOptionsService : updateOption: productId = "+productId+", optionId = "+optionId);
        productsRepository.findById(UUID.fromString(productId)).orElseThrow(()
                -> new ProductNotFoundException("Product Id "+productId+" not found"));
        productOptionsRepository.findById(UUID.fromString(optionId)).orElseThrow(()
                -> new ProductOptionsNotFoundException("Product Option "+optionId+" not found"));
        Product product = productsRepository.findById(UUID.fromString(productId)).get();
        newProductOptions.setId(UUID.fromString(optionId));
        newProductOptions.setProduct(product);
        return productOptionsRepository.save(newProductOptions);
    }

    @Transactional
    public void deleteOption(String productId, String optionId) {
        logger.info("ProductOptionsService : deleteOption : productId = "+productId+", optionId = "+optionId);
        productsRepository.findById(UUID.fromString(productId)).orElseThrow(()
                    -> new ProductNotFoundException("Product "+productId+" not found"));
        productOptionsRepository.findById(UUID.fromString(optionId)).orElseThrow(()
                    -> new ProductOptionsNotFoundException("Product Option "+optionId+" not found"));
        productOptionsRepository.deleteById(UUID.fromString(optionId));
    }
}
