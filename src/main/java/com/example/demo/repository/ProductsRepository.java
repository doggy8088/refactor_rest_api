package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Product;

public interface ProductsRepository extends JpaRepository<Product, String> {

    List<Product> findByName(String name);
}