package com.example.demo.repository;

import com.example.demo.models.ProductOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * This Repository interface talks to the Product Options Table in the Database.
 */
@Repository("productOptionsRepository")
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, UUID> {

    /* To delete the product options based on the Product ID */
    public void deleteByProductId(UUID productId);

    /* To List the product options based on the Product ID */
    public List<ProductOptions> findByProductId(UUID productId);

}
