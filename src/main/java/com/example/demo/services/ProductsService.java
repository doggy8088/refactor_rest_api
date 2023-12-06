package com.example.demo.services;

import static com.example.demo.exceptions.ProductNotFoundException.notFound;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductsRepository;

/**
 * This service class talks to the repositories for the data
 */
@Service
public class ProductsService {

    private static final Logger logger = LogManager.getLogger(ProductsService.class);

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    public List<Product> find(String name) {
        return productsRepository.findByName(name);
    }

    public Product findById(String id) {
        return productsRepository.findById(id).orElseThrow(() -> notFound(id));
    }

    public Product update(Product products) {
        logger.info("Update service called. Product Id = {}", products.getId());
        return productsRepository.save(products);
    }

    @Transactional
    public void deleteProduct(String id) {
        logger.info("Delete service called. Product Id = {}", id);
        productsRepository.deleteById(id);
    }
}
