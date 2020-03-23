package com.example.demo.services;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.models.Product;
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
public class ProductsService {

    private static final Logger logger = LogManager.getLogger(ProductsService.class);

    private ProductsRepository productsRepository;

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> findAll(){
        return productsRepository.findAll();
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    public List<Product> find(String name) {
        return productsRepository.findByName(name);
    }

    public Optional<Product> findById(UUID uuid) {
        return productsRepository.findById(uuid);
    }

    public Product update(String productId, Product updatedProduct) {
        logger.info("Update service called. Product Id = "+productId);
        productsRepository.findById(UUID.fromString(productId)).orElseThrow(()
                    -> new ProductNotFoundException("Product Id "+productId+" not found"));
        updatedProduct.setId(UUID.fromString(productId));
        return productsRepository.save(updatedProduct);
    }

    @Transactional
    public void deleteProduct(String productId){
        logger.info("Delete service called. Product Id = "+productId);
        productsRepository.findById(UUID.fromString(productId)).orElseThrow(()
                ->  new ProductNotFoundException("Product Id "+productId+" not found"));
        productOptionsRepository.deleteByProductId(UUID.fromString(productId));
        productsRepository.deleteById(UUID.fromString(productId));
    }
}
