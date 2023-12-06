package com.example.demo.controllers;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.services.ProductsService;
import com.example.demo.util.ProductResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Products")
@RestController
@RequestMapping("products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @ApiOperation(value = "gets all products")
    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts() {
        List<Product> productList = productsService.findAll();
        return ok(new ProductResponse(productList));
    }

    @ApiOperation(value = "creates a new product")
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product savedProduct = productsService.save(product);
        return created(URI.create("/products/" + savedProduct.getId())).body(savedProduct);
    }

    @ApiOperation(value = "finds all products matching the specified name")
    @GetMapping(params = "name")
    public ResponseEntity<ProductResponse> findProduct(@ApiParam(value = "name") @RequestParam String name) {
        List<Product> productList = productsService.find(name);
        return ok(new ProductResponse(productList));
    }

    @ApiOperation(value = "gets the project that matches the specified ID - ID is a UUID")
    @GetMapping("{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") String id) {
        Product product = productsService.findById(id);
        return ok(product);
    }

    @ApiOperation(value = "updates a product")
    @PutMapping()
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        Product updatedProduct = productsService.update(product);
        return ok(updatedProduct);
    }

    @ApiOperation(value = "deletes a product and its options")
    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        productsService.deleteProduct(id);
        return ok().build();
    }
}
