package com.example.demo.repository;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * This Repository interface talks to the Products Table in the Database.
 */
@Repository("productsRepository")
public interface ProductsRepository extends JpaRepository<Product, UUID> {

    /* To find the product by Name */
    public List<Product> findByName(String name);

}
